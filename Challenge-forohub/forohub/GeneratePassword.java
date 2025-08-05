import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456"; // La contraseña que quieres codificar
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Contraseña codificada: " + encodedPassword);
    }
}
