package com.study.phill;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args){

        //this import a path, always wtih double backslash
        Path source = Path.of("C:\\Users\\phill\\sample-images\\100-100-color\\30.jpg");
        Path target = Path.of("C:\\Users\\phill\\sample-images\\100-100-color\\30_thumb.png");

        boolean success = createThumbnail(source, target);
        System.out.println("sucess: "+success);



    }

    private static boolean createThumbnail(Path source, Path target) {

        //magick convert -resize 300x 32.jpg 32_thumb.png
        try {
            List<String> cmd = List.of("magick convert", "-resize", "300x", source.toString(), target.toString());
            ProcessBuilder builder = new ProcessBuilder(cmd);
            builder.inheritIO();
            Process process = builder.start();
            //garantimos que o processo aguardará 3 segundos para avançar
            boolean finished = process.waitFor(3, TimeUnit.SECONDS);

            if (!finished){
                //se nao tiver terminado nos 3 segundos, destrua o processo para nao gerar erro
                process.destroy();
            }
            return finished;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

}
