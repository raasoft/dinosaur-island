package gui.ingame.dinosaurislandgame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;



/**
    The ResourceManager class loads and manages tile Images and
    "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class ResourceManager {

	static private ResourceManager uniqueInstance;
	
    private Hashtable<Character, Image> stringToTiles;
    private Hashtable<Character, Image> stringToMiniTiles;
    private Hashtable<String, Image> stringToDinosaurImage;
    private Hashtable<InGameInformationIcon, Image> inGameInformationImage;
    private TileMap map;
    private Image buttonPressedOverlay;
    private Image statusBarBackground;
    private Image energyBox;
    private Image energyBox2;
    private Image dinosaurHerbivorePicture;
    private Image dinosaurCarnivorousPicture;
    private Image bottomPanel;
    private Image turnTimer;
    private Image mousePointer;
    private Image waitingForTurnChange;
    private Image gameOverBackground;
    private Image leaveGameBackground;
    private Image lostConnectionBackground;
    private Image exitConfirmation;
    private Image redArrow;

    
    private ArrayList<Image> layEggActionIcon;
    private ArrayList<Image> moveActionIcon;
    private ArrayList<Image> growActionIcon;
    
    private Font ingameFont;

    public enum InGameInformationIcon {
    	COMMAND_PERFORMED_SUCCESSFULLY, 
    	COMMAND_LEAD_TO_DEATH, 
    	COMMAND_GROW_SUCCESSFULLY,
    	COMMAND_GROW_UNSUCCESSFULLY,
    	COMMAND_LAY_EGG_SUCCESSFULLY,
    	COMMAND_LAY_EGG_UNSUCCESSFULLY,
    	COMMAND_MOVE_SUCCESSFULLY,
    	COMMAND_MOVE_UNSUCCESSFULLY,
    	COMMAND_PLAYERS_INFO;
    }

    /**
        Creates a new ResourceManager with the specified
        GraphicsConfiguration.
    */
    private ResourceManager(GraphicsConfiguration gc) {
        stringToTiles = new Hashtable<Character, Image>();
        stringToMiniTiles = new Hashtable<Character, Image>();
        stringToDinosaurImage = new Hashtable<String, Image>();
        inGameInformationImage = new Hashtable<InGameInformationIcon, Image>();
       
        map = new TileMap();
        
        loadFonts();
        loadGuiIcons();
        loadTileImages();
        loadDinosaurImage();
       // loadCreatureSprites();
    }
    
    public void setMap(TileMap theTileMap) {
    	map = theTileMap;
    }
    
    public Image getEnergyBox(boolean isUpside) {
    	if (isUpside)
    		return energyBox;
    	else
    		return energyBox2;
    }
    
    public TileMap getMap() {
    	return map;
    }
    
    public Image getButtonPressedOverlay() {
    	return buttonPressedOverlay;
    }
    
    public Image getExitConfirmation() {
    	return exitConfirmation;
    }
    
    public Image getWaitingForTurnChange() {
    	return waitingForTurnChange;
    }
    
    public Image getGameOverBackground() {
    	return gameOverBackground;
    }
    
    public Image getLeaveGameBackground() {
    	return leaveGameBackground;
    }
    
    public Image getLostConnectionBackground() {
    	return lostConnectionBackground;
    }
    
    public Image getBottomPanel() {
    	return bottomPanel;
    }
    
    public Image getTurnTimer() {
    	return turnTimer;
    }
    
    public Image getStatusBarBackground() {
    	return statusBarBackground;
    }
    
    public Font getInGameFont() {
    	return ingameFont;
    }
    
    public static ResourceManager getInstance() {
    	return uniqueInstance;
    }
    
    public static ResourceManager getInstance(GraphicsConfiguration gc) {
    	if (uniqueInstance == null) {
    		uniqueInstance = new ResourceManager(gc);
    	} 
    	
    	return uniqueInstance;
    }


    /**
        Gets an image from the images/ directory.
     * @throws Exception 
    */
    public Image loadImage(String name) {
    	
        File file = new File("images/"+name);
        if (!file.exists()) {
        	System.out.println("DOES NOT EXISTS");
         	throw new RuntimeException("Can't load :"+file.getAbsolutePath());
        }
    	
        String filename = "images/" + name;
        return new ImageIcon(filename).getImage();
    }

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

   /* private Image getScaledImage(Image image, float x, float y) {

        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate(
            (x-1) * image.getWidth(null) / 2,
            (y-1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(
            image.getWidth(null),
            image.getHeight(null),
            Transparency.BITMASK);

        // draw the transformed image
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }*/

    public Image getTileImage(Character theTileChar) {
    	return stringToTiles.get(theTileChar);
    }
    
    public Image getMiniTileImage(Character theMiniTileChar) {
    	return stringToMiniTiles.get(theMiniTileChar);
    }
    
    public Image getDinosaurImage(String theDinosaurImageString) {
    	return stringToDinosaurImage.get(theDinosaurImageString);
    }
    
    public Image getInGameInformationImage(InGameInformationIcon theInGameInformationImage) {
    	return inGameInformationImage.get(theInGameInformationImage);
    }
    
    public Image getRedArrow() {
    	return redArrow;
    }
    
    public Image getDinosaurHerbivorePicture() {
    	return dinosaurHerbivorePicture;
    }
    
    public Image getDinosaurCarnivorousPicture() {
    	return dinosaurCarnivorousPicture;
    }
    
    public Image getMousePointer() {
    	return mousePointer;
    }
    
    public void loadGuiIcons() {

    	mousePointer = loadImage("pointer.png");
    	
    	statusBarBackground = loadImage("LeftPanelContainer.png");
    	
    	turnTimer = loadImage("TimerRound.png");
    	bottomPanel = loadImage("BottomPanelBackground.png");
    	waitingForTurnChange = loadImage("WaitingServerTurn.png");
    	exitConfirmation = loadImage("ExitConfirmation.png");
    	
    	gameOverBackground = loadImage("GameOver.png");
    	lostConnectionBackground = loadImage("LostConnection.png");
    	leaveGameBackground = loadImage("EndGame.png");
    	
    	redArrow = loadImage("arrow_red.png");
    	
    	energyBox = loadImage("EnergyBox.png");
    	energyBox2 = loadImage("EnergyBox2.png");

    	layEggActionIcon = new ArrayList<Image>();
    	layEggActionIcon.add(loadImage("ButtonEgg.png"));
    	layEggActionIcon.add(loadImage("ButtonEggDisabled.png"));

    	moveActionIcon = new ArrayList<Image>();
    	moveActionIcon.add(loadImage("ButtonMove.png"));
    	moveActionIcon.add(loadImage("ButtonMoveDisabled.png"));    

    	growActionIcon = new ArrayList<Image>();
    	growActionIcon.add(loadImage("ButtonGrow.png"));
    	growActionIcon.add(loadImage("ButtonGrowDisabled.png"));
    	
    	buttonPressedOverlay = loadImage("ButtonPressedOverlay.png");

    	inGameInformationImage.put(InGameInformationIcon.COMMAND_PERFORMED_SUCCESSFULLY,loadImage("CommandPerformedSuccessfully.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_LEAD_TO_DEATH,loadImage("DeathIcon.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_GROW_SUCCESSFULLY,loadImage("GrowIconSuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_GROW_UNSUCCESSFULLY,loadImage("GrowIconUnsuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_LAY_EGG_SUCCESSFULLY,loadImage("LayEggIconSuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_LAY_EGG_UNSUCCESSFULLY,loadImage("LayEggIconUnsuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_MOVE_SUCCESSFULLY,loadImage("MovementIconSuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_MOVE_UNSUCCESSFULLY,loadImage("MovementIconUnsuccessful.png"));
    	inGameInformationImage.put(InGameInformationIcon.COMMAND_PLAYERS_INFO,loadImage("PlayersIcon.png"));
    }
    
    public Image getMoveActionIcon(boolean isEnabled) {
    	if (isEnabled) {
    		return moveActionIcon.get(0); 
    	} else {
    		return moveActionIcon.get(1);
    	}
    }
    
    public Image getLayEggActionIcon(boolean isEnabled) {
    	if (isEnabled) {
    		return layEggActionIcon.get(0); 
    	} else {
    		return layEggActionIcon.get(1);
    	}
    }
    
    public Image getGrowActionIcon(boolean isEnabled) {
    	if (isEnabled) {
    		return growActionIcon.get(0); 
    	} else {
    		return growActionIcon.get(1);
    	}
    }
    
    public void loadFonts() {
    	
    	File fontFile = new File("fonts/ad-lib-bt.ttf");
    	try {
			ingameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
    	
	
	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	     ge.registerFont(ingameFont);
		
		
    }
    
    
    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {

        stringToTiles.put(new Character('a'), loadImage("WaterCell32x32.png"));
        stringToTiles.put(new Character('t'), loadImage("TerrainCell32x32.png"));
        stringToTiles.put(new Character('v'), loadImage("VegetationCell32x32.png"));
        stringToTiles.put(new Character('b'), loadImage("UnknownCell32x32.png"));
        stringToTiles.put(new Character('c'), loadImage("CarrionCell32x32.png"));
        
    }
    
    public void loadDinosaurImage() {
        stringToDinosaurImage.put(new String("c"), loadImage("dinosaurCarnivorous.png"));
        stringToDinosaurImage.put(new String("e"), loadImage("dinosaurHerbivore.png"));
        
        
        dinosaurHerbivorePicture = loadImage("dinosaurHerbivoreShadowed.png");
        dinosaurCarnivorousPicture = loadImage("dinosaurCarnivorousShadowed.png");
        
    }

/*
    public void loadCreatureSprites() {

        Image[][] images = new Image[4][];

        // load left-facing images
        images[0] = new Image[] {
            loadImage("player1.png"),
            loadImage("player2.png"),
            loadImage("player3.png"),
            loadImage("fly1.png"),
            loadImage("fly2.png"),
            loadImage("fly3.png"),
            loadImage("grub1.png"),
            loadImage("grub2.png"),
        };

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];
        for (int i=0; i<images[0].length; i++) {
            // right-facing images
            images[1][i] = getMirrorImage(images[0][i]);
            // left-facing "dead" images
            images[2][i] = getFlippedImage(images[0][i]);
            // right-facing "dead" images
            images[3][i] = getFlippedImage(images[1][i]);
        }

        // create creature animations
        Animation[] playerAnim = new Animation[4];
        Animation[] flyAnim = new Animation[4];
        Animation[] grubAnim = new Animation[4];
        for (int i=0; i<4; i++) {
            playerAnim[i] = createPlayerAnim(
                images[i][0], images[i][1], images[i][2]);
            flyAnim[i] = createFlyAnim(
                images[i][3], images[i][4], images[i][5]);
            grubAnim[i] = createGrubAnim(
                images[i][6], images[i][7]);
        }

        flySprite = new Fly(flyAnim[0], flyAnim[1],
            flyAnim[2], flyAnim[3]);
        grubSprite = new Grub(grubAnim[0], grubAnim[1],
            grubAnim[2], grubAnim[3]);
    }
*/
/*
    private Animation createPlayerAnim(Image player1,
        Image player2, Image player3)
    {
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 150);
        anim.addFrame(player1, 150);
        anim.addFrame(player2, 150);
        anim.addFrame(player3, 200);
        anim.addFrame(player2, 150);
        return anim;
    }


    private Animation createFlyAnim(Image img1, Image img2,
        Image img3)
    {
        Animation anim = new Animation();
        anim.addFrame(img1, 50);
        anim.addFrame(img2, 50);
        anim.addFrame(img3, 50);
        anim.addFrame(img2, 50);
        return anim;
    }


    private Animation createGrubAnim(Image img1, Image img2) {
        Animation anim = new Animation();
        anim.addFrame(img1, 250);
        anim.addFrame(img2, 250);
        return anim;
    }*/


}
