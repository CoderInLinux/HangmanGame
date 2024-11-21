import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void drawingTheMan(int intentos) {
        switch (intentos) {
            case 0:
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                break;
            case 1:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                break;
            case 2:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println("     ");
                System.out.println("     ");
                break;
            case 3:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println(" /   ");
                System.out.println("     ");
                break;
            case 4:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");
                break;
            case 5:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("--|  ");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");
                break;
            case 6:
            default:
                System.out.println("     ");
                System.out.println("  0  ");
                System.out.println("--|--");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");
                break;
        }
    }

    public static ArrayList<String> leer(String nombreArchivo) throws FileNotFoundException, IOException {
        String auxs = "";

        ArrayList<String> palabrasLeidas = new ArrayList<>();
        FileReader fileReader = new FileReader(nombreArchivo);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((auxs = bufferedReader.readLine()) != null) {
            if (!auxs.isEmpty()) {
                palabrasLeidas.add(auxs);
            }
        }
        bufferedReader.close();
        return palabrasLeidas;
    }
    public static void main(String[] args) throws IOException {
        String palabraSecreta, palabraAdivinada;

        final int intentosMaximos = 7;
        int intentos = 0;
        int resultado = 0;

        boolean letraBien;
        ArrayList<String> palabras;

        try {
            palabras = leer("./src/lista-palabras.txt");
        } catch (FileNotFoundException e) {
            System.out.println("El archivo de palabras no existe");
            System.out.println("Imposible continuar");
            return;
        }

        Random rand = new Random();
        palabraSecreta = palabras.get(rand.nextInt(palabras.size()));

        palabraAdivinada = "_".repeat(palabraSecreta.length());

        Scanner consInp = new Scanner(System.in);

        while (resultado == 0) {
            System.out.println("\nPalabra: ");
            for (char c : palabraAdivinada.toCharArray()) {
                System.out.print(c + " ");
            }
            System.out.println("\nIntentos restantes: " + (intentosMaximos - intentos));
            drawingTheMan(intentos);

            System.out.print("\nIngresa una letra: ");
            String letraIngresada = consInp.next().trim().substring(0, 1);

            letraBien = false;
            int position = palabraSecreta.indexOf(letraIngresada);

            while (position != -1) {
                palabraAdivinada = palabraAdivinada.substring(0, position) + letraIngresada + palabraAdivinada.substring(position + 1);
                position = palabraSecreta.indexOf(letraIngresada, position + 1);
                letraBien = true;
            }

            if (!letraBien) {
                intentos++;
            }

            if (palabraAdivinada.equals(palabraSecreta)) {
                resultado = 1;
            } else if (intentos == intentosMaximos) {
                resultado = -1;
            }
        }

        System.out.println("\nResultado final:");
        drawingTheMan(intentos);
        if (resultado == 1) {
            System.out.println("\n¡Ganaste! La palabra secreta era: " + palabraSecreta);
        } else {
            System.out.println("\n¡Perdiste! La palabra secreta era: " + palabraSecreta);
        }
    }
}