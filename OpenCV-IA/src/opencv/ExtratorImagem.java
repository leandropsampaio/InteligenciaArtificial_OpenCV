package opencv;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGet2D;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

/**
 *
 * @author Leandro Pereira Sampaio
 */
public class ExtratorImagem {

    // Características do Homer e Bart
    float laranjaCamisaBart, azulCalcaoBart, azulSapatoBart;
    float azulCalcaHomer, marromBocaHomer, cinzaSapatoHomer;
    double red, green, blue;
    float[] caracteristicas = new float[6];

    public float[] extrairCaracteristicas(String caminho) {

        IplImage imagem = cvLoadImage(caminho);

        // Varre a imagem pixel a pixel
        for (int altura = 0; altura < imagem.height(); altura++) {
            for (int largura = 0; largura < imagem.width(); largura++) {

                // Extração do RGB de cada pixel da imagem
                opencv_core.CvScalar scalarExtraiRgb = cvGet2D(imagem, altura, largura);
                blue = scalarExtraiRgb.blue();
                green = scalarExtraiRgb.green();
                red = scalarExtraiRgb.red();

                // Camisa laranja do Bart                  
                if (blue >= 11 && blue <= 22
                        && green >= 85 && green <= 105
                        && red >= 240 && red <= 255) {

                    laranjaCamisaBart++;
                }

                // Calção azul do Bart (metade de baixo da imagem)
                if (altura > (imagem.height() / 2)) {
                    if (blue >= 125 && blue <= 170 && green >= 0 && green <= 12 && red >= 0 && red <= 20) {

                        azulCalcaoBart++;
                    }
                }

                // Sapato do Bart (parte inferior da imagem)
                if (altura > (imagem.height() / 2) + (imagem.height() / 3)) {
                    if (blue >= 125 && blue <= 140 && green >= 3 && green <= 12 && red >= 0 && red <= 20) {

                        azulSapatoBart++;
                    }
                }

                // Calça azul do Homer
                if (blue >= 150 && blue <= 180 && green >= 98 && green <= 120 && red >= 0 && red <= 90) {

                    azulCalcaHomer++;
                }

                // Boca do Homer (pouco mais da metade da imagem)
                if (altura < (imagem.height() / 2) + (imagem.height() / 3)) {
                    if (blue >= 95 && blue <= 140 && green >= 160 && green <= 185 && red >= 175 && red <= 200) {

                        marromBocaHomer++;
                    }
                }

                // Sapato do Homer (parte inferior da imagem)
                if (altura > (imagem.height() / 2) + (imagem.height() / 3)) {
                    if (blue >= 25 && blue <= 45 && green >= 25 && green <= 45 && red >= 25 && red <= 45) {

                        cinzaSapatoHomer++;
                    }
                }
            }
        }

        int quantPixelsTotal = imagem.height() * imagem.width();

        // Normaliza as características pelo número de pixels totais da imagem
        laranjaCamisaBart = (laranjaCamisaBart * 100) / quantPixelsTotal;
        azulCalcaoBart = (azulCalcaoBart * 100) / quantPixelsTotal;
        azulSapatoBart = (azulSapatoBart * 100) / quantPixelsTotal;
        azulCalcaHomer = (azulCalcaHomer * 100) / quantPixelsTotal;
        marromBocaHomer = (marromBocaHomer * 100) / quantPixelsTotal;
        cinzaSapatoHomer = (cinzaSapatoHomer * 100) / quantPixelsTotal;

        // Grava as características no vetor de características
        caracteristicas[0] = laranjaCamisaBart;
        caracteristicas[1] = azulCalcaoBart;
        caracteristicas[2] = azulSapatoBart;
        caracteristicas[3] = azulCalcaHomer;
        caracteristicas[4] = marromBocaHomer;
        caracteristicas[5] = cinzaSapatoHomer;

        return caracteristicas;
    }
}
