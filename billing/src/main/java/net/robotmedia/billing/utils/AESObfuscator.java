/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package net.robotmedia.billing.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;

/**
 * An obfuscator that uses AES to encrypt data.
 */
public class AESObfuscator {

	private static final String UTF8 = "UTF-8";

	private static final String KEYGEN_ALGORITHM = "PBEWITHSHAAND256BITAES-CBC-BC";
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static final byte[] IV = {16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74};

	public static final String SECURITY_PREFIX = "net.robotmedia.billing.utils.AESObfuscator-1|";

	@Nonnull
	private final Cipher encryptor;

	@Nonnull
	private final Cipher decryptor;

	public AESObfuscator(@Nonnull byte[] salt, String password) {
		try {
			// get algorithm by name
			final SecretKeyFactory factory = SecretKeyFactory.getInstance(KEYGEN_ALGORITHM);

			final KeySpec publicKeySpec = new PBEKeySpec(password.toCharArray(), salt, 1024, 256);
			final SecretKey tmp = factory.generateSecret(publicKeySpec);
			final SecretKey secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

			encryptor = Cipher.getInstance(CIPHER_ALGORITHM);
			encryptor.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV));

			decryptor = Cipher.getInstance(CIPHER_ALGORITHM);
			decryptor.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV));

		} catch (GeneralSecurityException e) {
			// This can't happen on a compatible Android device.
			throw new RuntimeException("Invalid environment", e);
		}
	}

	@Nullable
	public String obfuscate(@Nullable String source) {
		if (source == null) {
			return null;
		}

		try {
			// Header is appended as an integrity check
            final String in = SECURITY_PREFIX + source;
            byte[] inBytes = in.getBytes(UTF8);
            byte[] encrypted = encryptor.doFinal(inBytes);
            return Base64.encode(encrypted);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Invalid environment", e);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("Invalid environment", e);
		}
	}

	public String unobfuscate(String obfuscated) throws ValidationException {
		if (obfuscated == null) {
			return null;
		}

		try {
            final byte[] encryptedBytes = Base64.decode(obfuscated);
            final byte[] decryptedBytes = decryptor.doFinal(encryptedBytes);
            String result = new String(decryptedBytes, UTF8);
			// Check for presence of header. This serves as a final integrity check, for cases
			// where the block size is correct during decryption.
			int headerIndex = result.indexOf(SECURITY_PREFIX);
			if (headerIndex != 0) {
				throw new ValidationException("Header not found (invalid data or key)" + ":" +
						obfuscated);
			}
			return result.substring(SECURITY_PREFIX.length(), result.length());
		} catch (Base64DecoderException e) {
			throw new ValidationException(e.getMessage() + ":" + obfuscated);
		} catch (IllegalBlockSizeException e) {
			throw new ValidationException(e.getMessage() + ":" + obfuscated);
		} catch (BadPaddingException e) {
			throw new ValidationException(e.getMessage() + ":" + obfuscated);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Invalid environment", e);
		}
	}

	public class ValidationException extends Exception {

		private static final long serialVersionUID = 1L;

		public ValidationException() {
			super();
		}

		public ValidationException(String s) {
			super(s);
		}
	}

}