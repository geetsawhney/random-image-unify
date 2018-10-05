import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomImageApp {
    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.random.org/integers/?num=384&min=0&max=255&col=3&base=10&format=plain&rnd=new")
                .get().build();

        String responseInString = null;

        int[][] rgbMatrix = new int[128][3];

        try {
            Response response = client.newCall(request).execute();
            responseInString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = responseInString.split("\n");

        try {
            for (int i = 0; i < rgbMatrix.length; i++) {
                String[] line = lines[i].split("\\s");
                rgbMatrix[i][0] = Integer.parseInt(line[0]);
                rgbMatrix[i][1] = Integer.parseInt(line[1]);
                rgbMatrix[i][2] = Integer.parseInt(line[2]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                int i;
                if (random.nextInt(2) == 0)
                    i = x;
                else
                    i = y;
                image.setRGB(x, y, new Color(rgbMatrix[i][0], rgbMatrix[i][1], rgbMatrix[i][2]).getRGB());
            }
        }

        try {
            ImageIO.write(image, "bmp", new File("./example.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

