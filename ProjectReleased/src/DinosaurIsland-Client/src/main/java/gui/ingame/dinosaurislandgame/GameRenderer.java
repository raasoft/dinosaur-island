package gui.ingame.dinosaurislandgame;

import gui.ingame.core.Dinosaur;
import gui.ingame.core.DinosaurManager;
import gui.ingame.dinosaurislandgame.ResourceManager.InGameInformationIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import util.Position;


/**
    The TileMapRenderer class draws a TileMap on the screen.
    It draws all tiles, sprites, and an optional background image
    centered around the position of the player.

    <p>If the width of background image is smaller the width of
    the tile map, the background image will appear to move
    slowly, creating a parallax background effect.

    <p>Also, three static methods are provided to convert pixels
    to tile positions, and vice-versa.

    <p>This TileMapRender uses a tile size of 64.
*/
public class GameRenderer {

    static final int TILE_SIZE = 32;
    static final int TILE_SIZE_BITS = 5;
    
    static int MINIMAP_TILE_SIZE = 4;

    static int VIEW_LEFT_BORDER = 250;
    
    static int MINIMAP_Y = 10;
    static int MINIMAP_X = VIEW_LEFT_BORDER + MINIMAP_Y;
    
    static int PICTURE_SIZE = 192;
    static int PICTURE_Y = 2;
    static int PICTURE_X = VIEW_LEFT_BORDER/2-PICTURE_SIZE/2;
    
    static int ACTION_Y = PICTURE_Y + PICTURE_SIZE - 64 + 32;
    static int ACTION_X = 86;
    static int ACTION_SIZE = 30;
    
    static int EGGACTION_X = 60;
    static int MOVEACTION_X = EGGACTION_X + ACTION_SIZE+10;
    static int GROWACTION_X = MOVEACTION_X + ACTION_SIZE+10;
    
    static int STATUS_INFO_Y = ACTION_Y + ACTION_SIZE + 70;
    static int STATUS_INFO_X = 60;
    
    static int GAME_INFO_X = 25;
    static int GAME_INFO_IMAGE_SIZE = 24;
    static int GAME_INFO_TITLE_X = GAME_INFO_X + GAME_INFO_IMAGE_SIZE + 10;
    static int GAME_INFO_TITLE_YOFFSET = 24;
    static int GAME_INFO_YOFFSET = GAME_INFO_TITLE_YOFFSET +30;
    private String inGameInformationTitle;
   	private String inGameInformationText;
    private Image inGameInformationIcon;

	private int viewX = 0;
    private int viewY = 0;
    
    private int viewPivotX = 0;
    private int viewPivotPreviewX = 0;
	private int viewPivotY = 0;
	private int viewPivotPreviewY = 0;
	
	private int mouseX;
	private int mouseY;
	
	private int selectedTileX;
	private int selectedTileY;
	
	TileMap tileMap;
	
	private int screenWidth;
	private int screenHeight;
    
    boolean isScrolling = false;
    boolean isCellSelected = false;
    
    public void setInGameInformation(Image theCommandInfoIcon, String theTitle, String theInformation) {
    	
    	setInGameInformationIcon(theCommandInfoIcon);
    	setInGameInformationTitle(theTitle);
    	setInGameInformationText(theInformation);
    	
    }
    
    
    /**
	 * @return the isCellSelected
	 */
	public boolean isCellSelected() {
		return isCellSelected;
	}

	/**
	 * @param isCellSelected the isCellSelected to set
	 */
	public void setCellSelected(boolean theValue) {
		isCellSelected = theValue;
	}

	GameRenderer(int theScreenWidth, int theScreenHeight) {
    	screenWidth = theScreenWidth;
    	screenHeight = theScreenHeight;
    	
    	selectedTileX = -1;
    	selectedTileY = -1;
    	
    	Image infoDefaultIcon = ResourceManager.getInstance().getInGameInformationImage(InGameInformationIcon.COMMAND_PERFORMED_SUCCESSFULLY);
    	setInGameInformation(infoDefaultIcon, "Generic Info", "There are no useful in-game information yet.");
    }

    /**
	 * @return the isScrolling
	 */
	public boolean isScrolling() {
		return isScrolling;
	}


	/**
	 * @param isScrolling the isScrolling to set
	 */
	public void setScrolling(boolean theValue) {
		isScrolling = theValue;
	}
	
	/**
	 * @return the mouseX
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * @param mouseX the mouseX to set
	 */
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	/**
	 * @return the mouseY
	 */
	public int getMouseY() {
		return mouseY;
	}

	/**
	 * @param mouseY the mouseY to set
	 */
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}


	public int getViewX() {
		return viewX;
	}

	public void setViewX(int theValue) {
		
		if (theValue < 0) {
			theValue = 0;
		}
		
		 int mapWidth = tileMap.getWidth() * TILE_SIZE;
		
		if (theValue > mapWidth-screenWidth+VIEW_LEFT_BORDER) {
			theValue = mapWidth-screenWidth+VIEW_LEFT_BORDER;
		}
		
		viewX = theValue;
		
	}
	
	public boolean mouseIsWithinEggButton() {
		if (mouseX > EGGACTION_X && mouseX < EGGACTION_X + ACTION_SIZE) {
			if (mouseY > ACTION_Y && mouseY < ACTION_Y+ACTION_SIZE) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mouseIsWithinMoveButton() {
		if (mouseX > MOVEACTION_X && mouseX < MOVEACTION_X + ACTION_SIZE) {
			if (mouseY > ACTION_Y && mouseY < ACTION_Y+ACTION_SIZE) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mouseIsWithinGrowButton() {
		if (mouseX > GROWACTION_X && mouseX < GROWACTION_X + ACTION_SIZE) {
			if (mouseY > ACTION_Y && mouseY < ACTION_Y+ACTION_SIZE) {
				return true;
			}
		}
		return false;
	}
	
	public int getViewY() {
		return viewY;
	}

	public void setViewY(int theValue) {
		
		if (theValue < 0) {
			theValue = 0;
		}
		
		 int mapHeight = tileMap.getHeight() * TILE_SIZE;
			
			if (theValue > mapHeight-screenHeight) {
				theValue = mapHeight-screenHeight;
			}
		
		viewY = theValue;
		
		
		
	}
	
	public void updateScrolling(boolean mouseScrollButtonIsPressed) {
		
		if (mouseScrollButtonIsPressed && isScrolling() == false) {
			setScrolling(true);
			updateViewPivotPosition(getMouseX(), getMouseY());   
		}

		if (mouseScrollButtonIsPressed && isScrolling() == true) {
			updateViewPivotPreviewPosition(getMouseX(), getMouseY());
		}

		if (mouseScrollButtonIsPressed == false && isScrolling() == true ) {
			setScrolling(false);
			updateViewPivotPreviewPosition(getViewPivotX(), getViewPivotY());
			int newViewX = getViewX() + getViewPivotX() - getMouseX();
			int newViewY = getViewY() + getViewPivotY() - getMouseY();
			updateViewPosition(newViewX, newViewY);
		}

		update();

		
	}
	
	public int getViewPivotX() {
		return viewPivotX;
	}

	public void setViewPivotX(int theValue) {
		viewPivotX = theValue;
	}
	
	public int getViewPivotPreviewX() {
		return viewPivotPreviewX;
	}

	public void setViewPivotPreviewX(int theValue) {
		viewPivotPreviewX = theValue;
	}
	
	public int getViewPivotPreviewY() {
		return viewPivotPreviewY;
	}

	public void setViewPivotPreviewY(int theValue) {
		viewPivotPreviewY = theValue;
	}

	public int getViewPivotY() {
		return viewPivotY;
	}

	public void setViewPivotY(int theValue) {
		viewPivotY = theValue;
	}
	
	public void updateViewPivotPreviewPosition(int theValueX, int theValueY) {
		setViewPivotPreviewX(theValueX);
		setViewPivotPreviewY(theValueY);
	}
	
	public void updateViewPivotPosition(int theValueX, int theValueY) {
		setViewPivotX(theValueX);
		setViewPivotY(theValueY);
	}
	
	public void updateViewPosition(int theValueX, int theValueY) {
		setViewX(theValueX);
		setViewY(theValueY);
	}
    

    public static int pixelsToTilesX(float pixels) {
        return pixelsToTilesX(Math.round(pixels));
    }
    
    public static int pixelsToTilesY(float pixels) {
        return pixelsToTilesY(Math.round(pixels));
    }



    /**
        Converts a pixel position to a tile position.
    */
    public static int pixelsToTilesX(int pixels) {
        // use shifting to get correct values for negative pixels
        return pixels >> TILE_SIZE_BITS;

    }
    
    /**
    Converts a pixel position to a tile position.
*/
public static int pixelsToTilesY(int pixels) {
    
	return (40 * TILE_SIZE - pixels) >> TILE_SIZE_BITS;
}


    public static int tilesToPixelsX(int theCellX) {

        return (theCellX) * TILE_SIZE;  
    }
    
    public static int tilesToPixelsY(int theCellY) {

        return (39 - theCellY) * TILE_SIZE;  
    }
    
    

    public String getTileRelativeView() {
    	
    	int tileX = pixelsToTilesX(mouseX + getViewX() - VIEW_LEFT_BORDER);
    	int tileY = pixelsToTilesY(mouseY + getViewY());
    	
    	if (tileX >= 0 && tileX < 40 && tileY >= 0 && tileY < 40) {
    		selectedTileX = tileX;
    		selectedTileY = tileY;
    	}
    	
    	return "CellX: "+tileX+ " CellY: "+tileY;
    }
    
    public void setMousePosition(int theValueX, int theValueY) {
    	mouseX = theValueX;
    	mouseY = theValueY;
    }
    
    public Position getSelectedCell() {
    	if (selectedTileX >= 0 && selectedTileX <40 
    			&& selectedTileY >= 0 && selectedTileY < 40) {
    	
    		return new Position(selectedTileX, selectedTileY);
    		
    	}
    	else  {
    		return null;
    	}
    	
    	
    }
    
	/**
	 * @return the inGameInformationIcon
	 */
	public Image getInGameInformationIcon() {
		return inGameInformationIcon;
	}

	/**
	 * @param inGameInformationIcon the inGameInformationIcon to set
	 */
	void setInGameInformationIcon(Image inGameInformationIcon) {
		this.inGameInformationIcon = inGameInformationIcon;
	}
    
    /**
	 * @return the inGameInformationText
	 */
	public String getInGameInformationText() {
		return inGameInformationText;
	}

	/**
	 * @param inGameInformationText the inGameInformationText to set
	 */
	void setInGameInformationText(String inGameInformationText) {
		this.inGameInformationText = inGameInformationText;
	}
    
	 /**
	 * @return the inGameInformationTitle
	 */
	public String getInGameInformationTitle() {
		return inGameInformationTitle;
	}

	/**
	 * @param inGameInformationTitle the inGameInformationTitle to set
	 */
	 void setInGameInformationTitle(String inGameInformationTitle) {
		this.inGameInformationTitle = inGameInformationTitle;
	}
	
    public void update() {
    	getTileRelativeView();
    }
    
	/**
	 * @return the tileMap
	 */
	public TileMap getTileMap() {
		return tileMap;
	}

	/**
	 * @param tileMap the tileMap to set
	 */
	public void setTileMap(TileMap theTileMap) {
		tileMap = theTileMap;
	}
	
	private int getOffsetX() {
		
		int mapWidth = TILE_SIZE * tileMap.getWidth();
		
		int offsetX = getViewX() - getViewPivotPreviewX() + getViewPivotX();
		offsetX = Math.max(0, offsetX);
		offsetX = Math.min(mapWidth-screenWidth+VIEW_LEFT_BORDER, offsetX);

		return offsetX;
		
	}
	
	private int getOffsetY() {
		
		int mapHeight = TILE_SIZE * tileMap.getHeight();

		int offsetY = getViewY() - getViewPivotPreviewY() + getViewPivotY();
		offsetY = Math.max(0, offsetY);
		offsetY = Math.min(mapHeight-screenHeight, offsetY);
		
		return offsetY;
		
	}

    /**
        Draws the specified TileMap.
     */
	public void draw(Graphics2D g)
	{
		drawMap(g);
		
		if(isScrolling())
			drawMiniMap(g);
		
		drawStatusBar(g);
		
		drawGameInformation(g);
	}
	
	private void drawMap(Graphics2D g) {
		
		Font statusBarFont = new Font("Verdana", Font.PLAIN, 10);
		g.setFont(statusBarFont.deriveFont(10));

		int offsetX = getOffsetX();
		int offsetY = getOffsetY();

		g.setColor(Color.black);
		g.fillRect(VIEW_LEFT_BORDER, 0, screenWidth, screenHeight);

		// draw only the visible tiles
		int firstTileX = pixelsToTilesX(offsetX);
		int lastTileX = firstTileX +
		pixelsToTilesX(screenWidth) + 1;
		
		int lastTileY = pixelsToTilesY(offsetY)+1;
		int firstTileY = pixelsToTilesY(offsetY+screenHeight) - 1;

		for (int y=firstTileY; y<lastTileY; y++) {

			for (int x=firstTileX; x <= lastTileX; x++) {

				Image image = tileMap.getTile(x, y);
				if (image != null) {
					g.drawImage(image,
							tilesToPixelsX(x) - offsetX + VIEW_LEFT_BORDER,
							tilesToPixelsY(y) - offsetY, 
							null);
				}

				int lineX = tilesToPixelsX(x) - offsetX + VIEW_LEFT_BORDER;
				g.setColor(Color.black);
				g.drawLine(lineX, 0, lineX , screenHeight);

			}

			g.setColor(Color.black);
			int lineY = tilesToPixelsY(y) - offsetY;
			g.drawLine(VIEW_LEFT_BORDER, lineY, screenWidth, lineY );
		}
		
		for (Dinosaur dino : DinosaurManager.getInstance().getDinosaurList()) {

			if (dino.getPosition() == null)
				continue;
			
			Position dinoPos = dino.getPosition();
			
			int dinoY = dinoPos.getIntY();
			int dinoX = dinoPos.getIntX();

			int xPos = tilesToPixelsX(dinoX) - offsetX + VIEW_LEFT_BORDER;
			int yPos = tilesToPixelsY(dinoY) - offsetY;
			
			int dinoImageSize = 30 + dino.getSize(); 
			
			g.drawImage(dino.getImage(), xPos - dino.getSize() + 2, yPos - dino.getSize() + 2, dinoImageSize,dinoImageSize, null);
			
			if (DinosaurManager.getInstance().getPlayerDinosaurList().contains(dino)) {
				
				//g.drawImage(ResourceManager.getInstance().getRedArrow(), xPos + 10, yPos - 15, null);
				
				
				
				int dimX = (int) tileMap.getWidth();
				int dimY = (int) tileMap.getHeight();

				int dinoFOVRadius = (int) (2 + Math.floor(dino.getSize()/2));

				int bottomLeftX = dinoX - dinoFOVRadius;
				if (bottomLeftX < 0 )
					bottomLeftX = 0;

				int bottomLeftY = dinoY - dinoFOVRadius;
				if (bottomLeftY < 0 )
					bottomLeftY = 0;

				int topRightY = dinoY + dinoFOVRadius;
				if (topRightY > dimY-1)
					topRightY = dimY-1;

				int topRightX = dinoX + dinoFOVRadius;
				if (topRightX > dimX-1)
					topRightX = dimX-1;


				g.setColor(Color.ORANGE);

				for (int y = bottomLeftY; y <= topRightY; y++) {
					for (int x = bottomLeftX; x <= topRightX; x++) {
						g.drawRect(tilesToPixelsX(x) - offsetX + VIEW_LEFT_BORDER,
								tilesToPixelsY(y) - offsetY, TILE_SIZE, TILE_SIZE);					
					}
				}

			}
		}
		

		Position cellSourceSelected = tileMap.getCellSourceSelected();
		if (cellSourceSelected != null) {

			if (GameManager.getInstance().isMovingActionInProgress()) {
				Dinosaur theDino = DinosaurManager.getInstance().getDinosaur(tileMap.getDinosaurIDSource());

				int dimX = (int) tileMap.getWidth();
				int dimY = (int) tileMap.getHeight();

				Position dinoPos = theDino.getPosition();

				int dinoY = dinoPos.getIntY();
				int dinoX = dinoPos.getIntX();

				int dinoFOVRadius = theDino.getStepSize();

				int bottomLeftX = dinoX - dinoFOVRadius;
				if (bottomLeftX < 0 )
					bottomLeftX = 0;

				int bottomLeftY = dinoY - dinoFOVRadius;
				if (bottomLeftY < 0 )
					bottomLeftY = 0;

				int topRightY = dinoY + dinoFOVRadius;
				if (topRightY > dimY-1)
					topRightY = dimY-1;

				int topRightX = dinoX + dinoFOVRadius;
				if (topRightX > dimX-1)
					topRightX = dimX-1;

				
				g.setColor(Color.BLUE);

				for (int y = bottomLeftY; y <= topRightY; y++) {
					for (int x = bottomLeftX; x <= topRightX; x++) {
						g.drawRect(tilesToPixelsX(x) - offsetX + VIEW_LEFT_BORDER,
								tilesToPixelsY(y) - offsetY, TILE_SIZE, TILE_SIZE);					
					}
				}
			}
		}


		if (isScrolling() == false && selectedTileX >= 0 && selectedTileY >= 0) {
			g.setColor(Color.YELLOW);
			g.drawRect(tilesToPixelsX(selectedTileX) - offsetX + VIEW_LEFT_BORDER,
					tilesToPixelsY(selectedTileY) - offsetY, TILE_SIZE, TILE_SIZE);
		}

		
		for (Dinosaur dino : DinosaurManager.getInstance().getPlayerDinosaurList()) {

			if (dino.getPosition() == null)
				continue;
			
			Position dinoPos = dino.getPosition();
			
			int dinoY = dinoPos.getIntY();
			int dinoX = dinoPos.getIntX();

			int xPos = tilesToPixelsX(dinoX) - offsetX + VIEW_LEFT_BORDER;
			int yPos = tilesToPixelsY(dinoY) - offsetY;
			
		
			g.setColor(Color.YELLOW);
			g.drawRect(xPos, yPos, TILE_SIZE, TILE_SIZE);
			g.drawRect(xPos-1, yPos-1, TILE_SIZE+2, TILE_SIZE+2);
		}
		
		cellSourceSelected = tileMap.getCellSourceSelected();
		if (cellSourceSelected != null) {

			int xCellSelected = tilesToPixelsX(cellSourceSelected.getIntX()) - offsetX + VIEW_LEFT_BORDER;
			int yCellSelected = tilesToPixelsY(cellSourceSelected.getIntY()) - offsetY;
			g.setColor(Color.RED);
			g.drawRect(xCellSelected, yCellSelected, TILE_SIZE, TILE_SIZE);
			g.drawRect(xCellSelected-1, yCellSelected-1, TILE_SIZE+2, TILE_SIZE+2);

		}
		
		
		if (selectedTileX >= 0 && selectedTileY >= 0 && DinosaurManager.getInstance().isPositionInsidePlayerView(new Position(selectedTileX,selectedTileY))) {
			int energy = tileMap.getCellEnergy(new Position(selectedTileX,selectedTileY));

			if (energy >= 0) {

				int xCellEnergy = tilesToPixelsX(selectedTileX) - offsetX + VIEW_LEFT_BORDER;
				int yCellEnergy = tilesToPixelsY(selectedTileY) - offsetY;

				boolean isUpside = true;
				int offset = 0;

				if (yCellEnergy < 110) {
					offset = 1;
					isUpside = false;
				}

				g.drawImage(ResourceManager.getInstance().getEnergyBox(isUpside),
						xCellEnergy - 32, (int)(yCellEnergy - 55 * (1-1.4*offset)), null);


				g.setColor(Color.BLACK);
				g.setFont(statusBarFont.deriveFont(10));
				g.drawString("Energy: "+tileMap.getCellEnergy(
						new Position(selectedTileX,selectedTileY)),
						xCellEnergy-16,(int)(yCellEnergy - 32 * (1-3*offset)));
			}
		}
	}
	
	private void drawMiniMap(Graphics2D g) {
	    
		int pixX;
		int pixY;

		for (int y=0; y<tileMap.getHeight(); y++) {

			for (int x=0; x <= tileMap.getWidth(); x++) {

				pixX = tilesToPixelsX(x)/(TILE_SIZE /MINIMAP_TILE_SIZE)+ MINIMAP_X;
				pixY = tilesToPixelsY(y)/(TILE_SIZE /MINIMAP_TILE_SIZE) + MINIMAP_Y;
				
				Image image = tileMap.getTile(x, y);
				if (image != null) {
					g.drawImage(image, pixX, pixY, MINIMAP_TILE_SIZE, MINIMAP_TILE_SIZE, null);
				}
			}
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(MINIMAP_X, MINIMAP_Y, tileMap.getWidth()*(MINIMAP_TILE_SIZE) , tileMap.getHeight()*(MINIMAP_TILE_SIZE));
		
		g.setColor(Color.YELLOW);
		int xView = getOffsetX()/(TILE_SIZE /MINIMAP_TILE_SIZE);
		int yView = getOffsetY()/(TILE_SIZE /MINIMAP_TILE_SIZE);
		int xRect = (screenWidth-VIEW_LEFT_BORDER)/(TILE_SIZE /MINIMAP_TILE_SIZE);
		int yRect = (screenHeight)/(TILE_SIZE /MINIMAP_TILE_SIZE);
		
		g.drawRect(MINIMAP_X+xView, MINIMAP_Y+yView, xRect, yRect);
	}

	
	
	private void drawStatusBar(Graphics2D g) {
		
		Font statusBarFont = new Font("Arial Black", Font.PLAIN, 12);
		g.setFont(statusBarFont.deriveFont(12));

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, VIEW_LEFT_BORDER, screenHeight);
		
		g.drawImage(
				ResourceManager.getInstance().getStatusBarBackground(),
				0, 0, null);
		
		if (tileMap.getDinosaurIDSource() != null) {

			Dinosaur theDinosaur = DinosaurManager.getInstance().getDinosaur(tileMap.getDinosaurIDSource());

			if (theDinosaur.getSpeciesType().equals(new Character('c'))) {
				g.drawImage(ResourceManager.getInstance().getDinosaurCarnivorousPicture(),
						PICTURE_X, PICTURE_Y, null);
			} else {
				g.drawImage(ResourceManager.getInstance().getDinosaurHerbivorePicture(),
						PICTURE_X, PICTURE_Y, null);	
			}		
			
			int offX = STATUS_INFO_X;

			g.drawImage(
					ResourceManager.getInstance().getLayEggActionIcon(GameManager.getInstance().isCanPerformLayEgg()), 
					EGGACTION_X, ACTION_Y, null);

			g.drawImage(
					ResourceManager.getInstance().getMoveActionIcon(GameManager.getInstance().isCanPerformLayEgg()), 
					MOVEACTION_X, ACTION_Y, null);

			g.drawImage(
					ResourceManager.getInstance().getGrowActionIcon(GameManager.getInstance().isCanPerformLayEgg()), 
					GROWACTION_X, ACTION_Y, null);
			
			if (mouseIsWithinEggButton() && GameManager.getInstance().isCanPerformLayEgg()) {
				g.drawImage(ResourceManager.getInstance().getButtonPressedOverlay(), EGGACTION_X, ACTION_Y, null);
			} else if (mouseIsWithinGrowButton() && GameManager.getInstance().isCanPerformGrow()) {
				g.drawImage(ResourceManager.getInstance().getButtonPressedOverlay(), GROWACTION_X, ACTION_Y, null);
			} else if (mouseIsWithinMoveButton() && GameManager.getInstance().isCanPerformMove()) {
				g.drawImage(ResourceManager.getInstance().getButtonPressedOverlay(), MOVEACTION_X, ACTION_Y, null);
			}

			g.setColor(Color.WHITE);
			
			int offY = STATUS_INFO_Y;
			
			int actionCost = -1; 
			if (mouseIsWithinEggButton() && GameManager.getInstance().isCanPerformLayEgg()) {
				actionCost = 1500;
			} else if (mouseIsWithinGrowButton() && GameManager.getInstance().isCanPerformGrow()) {
				actionCost = 1000*theDinosaur.getSize()/2;
			} else if (GameManager.getInstance().isMovingActionInProgress()) {
				
				Position source = tileMap.getCellSourceSelected();
				Position dest = getSelectedCell();
				
				int xDistance = Math.abs(source.getIntX() - dest.getIntX());
				int yDistance = Math.abs(source.getIntY() - dest.getIntY());
				
				int maxDistance = Math.max(xDistance, yDistance);
				
				if (maxDistance > 0) {
				
					if (maxDistance <= 2 && theDinosaur.getSpeciesType().equals(new Character('e')))
						actionCost = 10 * (int) Math.pow(2, maxDistance);
					if (maxDistance <= 3 && theDinosaur.getSpeciesType().equals(new Character('c')))
						actionCost = 10 * (int) Math.pow(2, maxDistance);
				
				}
			}
			
			
			String energyDrop = " ";
			
			if (actionCost > 0) {
				energyDrop = " - "+actionCost;
			}
		
			offY = STATUS_INFO_Y;
			int vOffset = 20;			

			String toBeDisplayed;
			
			toBeDisplayed = theDinosaur.getOwnerUsername();
			
			if (toBeDisplayed.length() > 15)
				toBeDisplayed = toBeDisplayed.substring(0,15)+"...";
			
			g.drawString(""+toBeDisplayed, offX + 8, offY ); offY+=vOffset;
			
			toBeDisplayed = theDinosaur.getSpeciesName();
			
			if (toBeDisplayed.length() > 15)
				toBeDisplayed = toBeDisplayed.substring(0,15)+"...";
			
			g.drawString(""+toBeDisplayed, offX + 25, offY); offY+=vOffset;

			if (theDinosaur.getSpeciesType().equals('e')) {
				g.drawString("herbivore", offX + 10, offY); offY+=vOffset;
			}
			else {
				g.drawString("carnivorous", offX + 10, offY); offY+=vOffset;
			}

			g.drawString(""+theDinosaur.getSize() + " / 5",offX, offY ); offY+=vOffset;
			g.drawString(""+theDinosaur.getPosition(),offX + 25, offY); offY+=vOffset;

			FontMetrics fm = g.getFontMetrics();
			int stringWidth;
	    	
			if (theDinosaur.getEnergy() >= 0) {
				
				g.drawString(""+theDinosaur.getEnergy(),offX + 20, offY); 
				toBeDisplayed = ""+theDinosaur.getEnergy();
				stringWidth = fm.stringWidth(toBeDisplayed);
				
				if (theDinosaur.getEnergy() < actionCost)
					g.setColor(Color.RED);
				else
					g.setColor(Color.GREEN);
				
				g.drawString(energyDrop ,offX + 20 + stringWidth, offY);
				
				g.setColor(Color.YELLOW);
				
				g.drawString(" / "+(1000*theDinosaur.getSize()) ,offX + 20 + stringWidth + fm.stringWidth(energyDrop), offY); offY+=vOffset;
				
				g.setColor(Color.WHITE);

			}
			else {
				g.drawString("??? / ???",offX + 20, offY); offY+=vOffset;
			}
	    	
			if (theDinosaur.getTurnsAlive() > 0) {
				g.drawString(""+theDinosaur.getTurnsAlive(),offX, offY); offY+=vOffset;
			} else if (theDinosaur.getTurnsAlive() == 0) {
				g.drawString("newborn", offX, offY); offY+=vOffset;
			}
			else {
				g.drawString("???",offX, offY); offY+=vOffset;
			}

		}
	}
	

	private void drawGameInformation(Graphics2D g) {
		
		Font gameInformationFont = new Font("Verdana", Font.BOLD, 20);
		Font gameInformationTitleFont = new Font("Verdana", Font.BOLD, 12);
		Font gameInformationTextFont = new Font("Verdana", Font.PLAIN, 12);
		g.setColor(Color.WHITE);
		g.setFont(gameInformationFont.deriveFont(20));
		
		final int GAME_INFO_Y = screenHeight/2 + 160;
		
		g.drawImage(getInGameInformationIcon(), GAME_INFO_X, GAME_INFO_Y + 6, null);
		
		g.setFont(gameInformationTitleFont);
		drawString(g, getInGameInformationTitle(), GAME_INFO_TITLE_X, GAME_INFO_Y + GAME_INFO_TITLE_YOFFSET, VIEW_LEFT_BORDER - GAME_INFO_TITLE_X - 10);
		
		g.setFont(gameInformationTextFont);
		drawString(g, getInGameInformationText(), GAME_INFO_X, GAME_INFO_Y + GAME_INFO_YOFFSET, VIEW_LEFT_BORDER - GAME_INFO_X - 10);
		
	}

	public void drawString(Graphics2D g, String s, int x, int y, int width)
	{
	        // FontMetrics gives us information about the width,
	        // height, etc. of the current Graphics object's Font.
	        FontMetrics fm = g.getFontMetrics();

	        int lineHeight = fm.getHeight();

	        int curX = x;
	        int curY = y;

	        String[] words = s.split(" ");

	        for (String word : words)
	        {
	                // Find out thw width of the word.
	                int wordWidth = fm.stringWidth(word + " ");

	                // If text exceeds the width, then move to next line.
	                if (curX + wordWidth >= x + width || word.trim().equals("\n"))
	                {
	                        curY += lineHeight;
	                        curX = x;
	                }

	                g.drawString(word, curX, curY);

	                // Move over to the right for next word.
	                curX += wordWidth;
	        }
	}
	
}
