import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Square{
        private int xPos;
        private int yPos;
        private int width;
        private int height;
        private char tile;
        private BufferedImage image;

        public Square(int x, int y, char tile) throws IOException{
                xPos = x;
                yPos = y;
                width = height = 32;
                this.tile = tile;
                image = getImageFromTile(tile);
        }

        public BufferedImage  getImageFromTile(char tile) throws IOException {
                BufferedImage retImg;
                switch(tile){
                case '#': retImg = ImageIO.read(new File("resources/black.bmp"));
                        break;
                case '.': retImg = ImageIO.read(new File("resources/white.bmp"));
                        break;
                case 'o': retImg = ImageIO.read(new File("resources/start.bmp"));
                        break;
                case '*': retImg = ImageIO.read(new File("resources/end.bmp"));
                        break;
                case 'v': retImg = ImageIO.read(new File("resources/grey.bmp"));
                        break;
                default: retImg = ImageIO.read(new File("resources/unknown.bmp"));
                        break;
                }
                return retImg;
        }

        public void setX(int x) {
                xPos = x;
        }

        public void setY(int y) {
                yPos = y;
        }

        public void setPos(int x, int y) {
                xPos = x;
                yPos = y;
        }

        public int getWidth(){
                return width;
        }

        public int getHeight() {
                return height;
        }

        public void paintSquare(Graphics g) {
                g.drawImage(image, xPos, yPos, null);
        }

        public void setTile(char tile) {
                this.tile = tile;
                try {
                        image = getImageFromTile(tile);
                }
                catch(IOException e){System.out.println("Could not update square tile");}

        }
}
