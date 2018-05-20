package com.nchu.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

@Controller
public class QRcodeController {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


    @GetMapping(value = "toQRcode")
    public String toQRcode(){
        return "admin/QRcode";
    }



    @GetMapping(value = "QRCode")
    public void QRCode(HttpServletResponse response) throws IOException {

        String text="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc364d1d3f0307377&redirect_uri=http://nchuwechat.s1.natapp.cc/weixin/auth&response_type=code&scope=snsapi_userinfo&state="+"1号桌子"+"#wechat_redirect";

        int width = 600;    //二维码图片的宽
        int height = 600;   //二维码图片的高
        String format = "png";  //二维码图片的格式


        response.setContentType("application/octet-stream");
       // response.setHeader("Content-Disposition", "attachment;filename=car_test.xls");
        ServletOutputStream out = response.getOutputStream();

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            ImageIO.write(image, format, out);
            out.flush();
            out.close();

        }catch (Exception e){

        }



    }


}
