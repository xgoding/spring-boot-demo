package com.xgoding.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @package: com.xgoding.thumbnailator
 * @description:
 * @author: yxguang
 * @date: 2020/12/17
 * @version: V1.0
 * @modified: yxguang
 */
public class Thumbnailator {
    public static void main(String[] args) throws IOException {
        comprise("C:\\Users\\yxguang\\Desktop\\测试照片\\source", "C:\\Users\\yxguang\\Desktop\\测试照片\\output");
    }

    public static void comprise(String sourcePath, String outputPath) throws IOException {
        BufferedImage bi = new BufferedImage(400, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.CYAN);
        Font font = new Font("Serif", Font.ITALIC, 50);
        g.setFont(font);
        g.drawString("xgoding", 0, 0);
        Thumbnails.of(Objects.requireNonNull(new File(sourcePath).listFiles()))
                .size(1200, 1200)
                .outputFormat("jpg")
                .watermark(Positions.TOP_LEFT, bi, 0.5f)
                .toFiles(new File(outputPath), Rename.PREFIX_DOT_THUMBNAIL);
    }
}
