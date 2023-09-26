Cryptography Project
Authors: Ian McLean and Patrick Tibbals

The menu will provide two initial options:

    1- symmetric(Part 1)
        A - Compute a plain cryptographic hash
        B - Compute an authentication tag (MAC)
        C - Encrypt a given data file
        D - Decrypt a given symmetric cryptogram

    2- elliptic(Part 2)
        A - Generate elliptic key pair (s,V)
            - Enter the file path you want to write the public key to
            - Enter the file path you want to write the private key to
            - Enter your password
        B - Encrypt a given data file
            - Enter the file path with the public key
            - Enter 1 to input custom text or 2 for file loading
            - Enter text for encryption
            - Enter the file path you want to write the ciphertext to
            - Encoded successfully
        C - Decrypt a given symmetric cryptogram
            - Enter file path of ciphertext:
            - Please enter the password: 
            - Accepted Input or Incorrect t != t'
        D - Sign a file
            - Enter the file path with the public key:
            - Enter 1 to input custom text or 2 for file loading
            - Enter text for signing
                * Same text used for verify
            - Enter passphrase
                * Passphrase used for the public key
            - Enter the file path you want to write the signature to: 
        E - Verify file and signature
            - Enter the file path with the public key:
            - Enter the file path with the text you want signature verified
                * Same text used for signing
            - Enter the file path with the signature:
            - Verified: Correct Signature or Incorrect Signature

Before any of the other asymmetric functions can be used you must generate a key pair (obviously).
So make sure to run A first.