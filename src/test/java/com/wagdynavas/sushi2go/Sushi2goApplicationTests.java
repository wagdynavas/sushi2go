package com.wagdynavas.sushi2go;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
class Sushi2goApplicationTests {

    /*@Test
    public void testJasypt() {
        Set supported = new HashSet();
        Set unsupported = new HashSet<>();
        for (Object algorithms : AlgorithmRegistry.getAllPBEAlgorithms()) {
            String algo = (String) algorithms;
            try {
                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                encryptor.setAlgorithm(algo);
                encryptor.setPassword("kingkong");
                encryptor.setIvGenerator(new RandomIvGenerator());
                String encrypted = encryptor.encrypt("foo");
                String decrypted = encryptor.decrypt(encrypted);


                Assertions.assertEquals(decrypted, "foo");
                supported.add(algo);
            } catch (EncryptionOperationNotPossibleException e) {
                unsupported.add(algo);
            }
        }
    }*/

    @Test
    void test() {
        System.out.println("Test");
    }

}
