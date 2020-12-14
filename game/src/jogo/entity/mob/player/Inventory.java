package jogo.entity.mob.player;

import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.logging.Logger;

import jogo.entity.mob.Player;
import jogo.events.Event;
import jogo.events.EventDispatcher;
import jogo.events.types.MouseMovedEvent;
import jogo.events.types.MousePressedEvent;
import jogo.events.types.MouseReleasedEvent;
import jogo.graphics.layers.Layer;
import jogo.graphics.ui.UIInventoryCell;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class Inventory extends Layer {
	private UIManager ui;
	private SharedCell[] cells = new SharedCell[6];
	private UIPanel favoritItemsPanel;
	private UIPanel inventoryPanel;
	private Player player;
	private ArrayList<Item> items = new ArrayList<Item>();
	private UIInventoryCell from, moving, to;
	private boolean inventoryOpen = false;

	private Item lifePotion;
	private Item recoverLifePotion;
	private Item manaPotion;
	private Item recoverManaPotion;
	
	public Inventory(UIManager ui, Player player) {
		this.ui = ui;
		this.player = player;
		initializeItems();
		

		inventoryPanel = new UIPanel(new Vector2i(100, 200), new Vector2i(530, 210));

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 8; x++) {
				if (y == 0 && x < 6) {
					cells[x] = new SharedCell(new Vector2i(10 + x * UIInventoryCell.DEFAULTSIZE.x + 15 * x,
							10 + y * UIInventoryCell.DEFAULTSIZE.y + 15 * y), SharedCell.DEFAULTSIZE);
					cells[x].setNumberToBeRendered(x + 1, new Vector2i(115, 235));
					inventoryPanel.addComponent(cells[x]);
					continue;
				}
				inventoryPanel
						.addComponent(new UIInventoryCell(new Vector2i(10 + x * UIInventoryCell.DEFAULTSIZE.x + 15 * x,
								10 + y * UIInventoryCell.DEFAULTSIZE.y + 15 * y), UIInventoryCell.DEFAULTSIZE));

			}
		}
		cells[0].setItem(lifePotion);
		cells[1].setItem(manaPotion);
		cells[2].setItem(recoverManaPotion);
		cells[3].setItem(recoverLifePotion);

		favoriteItemsInit();
	}

	private void initializeItems() {
		lifePotion = new Item("/items/lifePotion.png", true, 5);
		lifePotion.setActionListener(() -> {
			player.addOrRemoveHealth(40);
		});
		
		recoverLifePotion = new Item("/items/recoverLifePotion.png", true, 5);
		recoverLifePotion.setActionListener(() -> {
			player.Heal(10, 3);
		});
		
		manaPotion = new Item("/items/manaPotion.png", true, 5);
		manaPotion.setActionListener(() -> {
			player.addOrRemoveMana(35);
		});
		
		recoverManaPotion= new Item("/items/recoverManaPotion.png", true, 5);
		recoverManaPotion.setActionListener(() -> {
			player.recoverMana(10, 3);
		});
	}

	public void favoriteItemsInit() {
		favoritItemsPanel = new UIPanel(new Vector2i(170, 440), new Vector2i(400, 80));
		favoritItemsPanel.setColor(0x10606060);

		cells[0].setPositionOnInventory(new Vector2i(13, 10));
		cells[1].setPositionOnInventory(new Vector2i(UIInventoryCell.DEFAULTSIZE.x + 13 * 2, 10));
		cells[2].setPositionOnInventory(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 2 + 13 * 3, 10));
		cells[3].setPositionOnInventory(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 3 + 13 * 4, 10));
		cells[4].setPositionOnInventory(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 4 + 13 * 5, 10));
		cells[5].setPositionOnInventory(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 5 + 13 * 6, 10));

		cells[0].setKeyToObserv(Keyboard.item1);
		cells[1].setKeyToObserv(Keyboard.item2);
		cells[2].setKeyToObserv(Keyboard.item3);
		cells[3].setKeyToObserv(Keyboard.item4);
		cells[4].setKeyToObserv(Keyboard.item5);
		cells[5].setKeyToObserv(Keyboard.item6);

		for (int i = 0; i < cells.length; i++) {
			favoritItemsPanel.addComponent(cells[i]);
		}

		items.add(lifePotion);
		cells[0].setItem(lifePotion);
		ui.addPanel(favoritItemsPanel);

	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMooved((MouseMovedEvent) e));
	}

	public boolean onMousePressed(MousePressedEvent e) {
		from = (UIInventoryCell) inventoryPanel.getComponentAt(new Vector2i(e.getX(), e.getY()));
		if (from == null || from.getItem() == null)
			from = null;
		else {
			moving = new MovingCell(new Vector2i(e), UIInventoryCell.DEFAULTSIZE, from.getItem());
			inventoryPanel.addComponent(moving);			
		}
		return true;
	}

	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (moving == null)
			return true;
		to = (UIInventoryCell) inventoryPanel.getComponentAt(new Vector2i(e.getX(), e.getY()));

		if (to != null && to.getItem() == null) {
			to.setItem(from.getItem());
			from.setItem(null);
		} else if (to != null) {
			Item temp = to.getItem();
			to.setItem(from.getItem());
			from.setItem(temp);
		}
		inventoryPanel.removeComponent(moving);
		moving = from = to = null;
		return true;
	}

	public boolean onMouseMooved(MouseMovedEvent e) {
		if (from != null & e.getDragged())
			moving.setPosition(new Vector2i(e.getX(), e.getY()));
		return true;
	}


	public void setVisibility(boolean visibility) {
		if (visibility) {
			ui.addPanel(inventoryPanel);
			inventoryOpen = true;
		} else {
			ui.removePanel(inventoryPanel);
			inventoryOpen = false;
		}

	}

	public class MovingCell extends UIInventoryCell {
		public MovingCell(Vector2i position, Vector2i size, Item item) {
			super(position, size, item);
		}

		public void render(Graphics g) {
			if (super.getItem() != null)
				g.drawImage(getItem().getIcom(), position.x - (size.x >> 1), position.y - (size.y >> 1), size.x, size.y,
						null);

		}
	}

	public class SharedCell extends UIInventoryCell {
		private Vector2i positionOnInventory;
		private Vector2i offsetOnInventory;

		public SharedCell(Vector2i position, Vector2i size, Item item) {
			super(position, size, item);
			offsetOnInventory = new Vector2i(173, 440);
		}

		public SharedCell(Vector2i position, Vector2i size) {
			super(position, size);
			offsetOnInventory = new Vector2i(173, 440);
		}

		public SharedCell(Vector2i position, Vector2i size, int keyToListen) {
			super(position, size, keyToListen);
			offsetOnInventory = new Vector2i(173, 440);
		}

		public void setPositionOnInventory(Vector2i positionOnInventory) {
			this.positionOnInventory = positionOnInventory;
		}

		public void setOffsetOnInventory(Vector2i offsetOnInventory) {
			this.offsetOnInventory = offsetOnInventory;
		}

		public void update() {
			if (super.getItem() != null && super.getItem().getConsumable()
					&& Keyboard.firstPress(super.getKeyToListen())) {
				super.getItem().performAction();
				super.getItem().used();
				if(super.getItem().getQuantity() <=0)
					super.setItem(null);
			}
		}

		public void render(Graphics g) {
			{
				int x = position.x + offset.x;
				int y = position.y + offset.y;
				
				g.setColor(color);
				g.fillRect(x, y, size.x, size.y);

				if (super.getItem() != null)
					g.drawImage(super.getItem().getIcom(), x, y, size.x, size.y, null);
			}

			if (inventoryOpen) {
				if (super.getNumberToBeRendered() != null)
					super.getNumberToBeRendered().render(g);

				int x = positionOnInventory.x + offsetOnInventory.x;
				int y = positionOnInventory.y + offsetOnInventory.y;
				
				g.setColor(color);
				g.fillRect(x, y, size.x, size.y);

				if (super.getItem() != null)
					g.drawImage(super.getItem().getIcom(), x, y, size.x, size.y, null);

			}
		}
	}

}
