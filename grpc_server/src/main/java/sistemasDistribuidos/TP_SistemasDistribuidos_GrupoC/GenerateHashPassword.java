package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateHashPassword {

    @Autowired
    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        //Generamos una clave aleatoria de 12 caracteres
        String clave = RandomStringUtils.randomAlphabetic(12);
        //Una vez generada, la seteamos y la encriptamos
        System.out.println("\nClave sin hashear: " + clave);
        System.out.println("\nClave hasheada: " + bCryptPasswordEncoder.encode(clave).toString());
    }
}
