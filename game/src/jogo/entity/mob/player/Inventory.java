package jogo.entity.mob.player;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Logger;

import jogo.Game;
import jogo.entity.mob.Player;
import jogo.events.Event;
import jogo.events.EventDispatcher;
import jogo.events.types.MouseMovedEvent;
import jogo.events.types.MousePressedEvent;
import jogo.events.types.MouseReleasedEvent;
import jogo.graphics.Screen;
import jogo.graphics.layers.Layer;
import jogo.graphics.ui.UIInventoryCell;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.input.Keyboard;
import jogo.util.Vector2i;

public class Inventory extends Layer {
	private UIManager ui;
	private UIInventoryCell[] cells = new UIInventoryCell[6];
	private UIPanel favoritItemsPanel;
	private UIPanel inventoryPanel;
	private Player player;
	private ArrayList<Item> items = new ArrayList<Item>();
	private UIInventoryCell from, moving, to;
	private Item lifePotion = new Item("/items/lifePotion.png", true, 100);

	public Inventory(UIManager ui, Player player) {
		this.ui = ui;
		this.player = player;
		lifePotion.setActionListener(() -> {
			player.addOrRemoveHealth(40);
			Logger.getGlobal().info("done");
		});
		favoriteItemsInit();

		inventoryPanel = new UIPanel(new Vector2i(100, 200), new Vector2i(530, 210));

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 8; x++) {
				inventoryPanel
						.addComponent(new UIInventoryCell(new Vector2i(10 + x * UIInventoryCell.DEFAULTSIZE.x + 15 * x,
								10 + y * UIInventoryCell.DEFAULTSIZE.y + 15 * y), UIInventoryCell.DEFAULTSIZE));
			}
		}
		UIInventoryCell first = (UIInventoryCell) inventoryPanel.getComponentAt(new Vector2i(20, 20));
		first.setItem(lifePotion);

	}

	public void favoriteItemsInit() {
		favoritItemsPanel = new UIPanel(new Vector2i(170, 440), new Vector2i(400, 80));
		favoritItemsPanel.setColor(0x10606060);

		cells[0] = new UIInventoryCell(new Vector2i(10, 10), UIInventoryCell.DEFAULTSIZE, Keyboard.item1);
		cells[1] = new UIInventoryCell(new Vector2i(UIInventoryCell.DEFAULTSIZE.x + 10 * 2, 10),
				UIInventoryCell.DEFAULTSIZE);
		cells[2] = new UIInventoryCell(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 2 + 10 * 3, 10),
				UIInventoryCell.DEFAULTSIZE);
		cells[3] = new UIInventoryCell(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 3 + 10 * 4, 10),
				UIInventoryCell.DEFAULTSIZE);
		cells[4] = new UIInventoryCell(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 4 + 10 * 5, 10),
				UIInventoryCell.DEFAULTSIZE);
		cells[5] = new UIInventoryCell(new Vector2i(UIInventoryCell.DEFAULTSIZE.x * 5 + 10 * 6, 10),
				UIInventoryCell.DEFAULTSIZE);

		for (UIInventoryCell c : cells)
			favoritItemsPanel.addComponent(c);

		items.add(lifePotion);
		cells[0].setItem(lifePotion);
		ui.addPanel(favoritItemsPanel);

	}

	public void onEvent(Event event) {
		Logger.getGlobal().info("reciving" + event.getType());
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMooved((MouseMovedEvent) e));
	}

	public boolean onMousePressed(MousePressedEvent e) {
		from = (UIInventoryCell) inventoryPanel.getComponentAt(new Vector2i(e.getX(), e.getY()));
		if (from == null || from.getItem() == null)
			from = null;
		else
			moving = new movingCell(new Vector2i(e), UIInventoryCell.DEFAULTSIZE, from.getItem());
			inventoryPanel.addComponent(moving);
		return true;
	}

	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (from == null)
			return true;
		to = (UIInventoryCell) inventoryPanel.getComponentAt(new Vector2i(e.getX(), e.getY()));

		if (to == null || to.getItem() == null) {
			to.setItem(from.getItem());
			from.setItem(null);
		} else {
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

	public void render(Screen screen) {

	}

	class dinamicCell {
		private UIInventoryCell cell;

		public dinamicCell(UIInventoryCell cell) {
			this.cell = cell;
		}

		public void update() {

		}
	}

	public void setVisibility(boolean visibility) {
		if (visibility)
			ui.addPanel(inventoryPanel);
		else
			ui.removePanel(inventoryPanel);

	}
	
	public class movingCell extends UIInventoryCell{
		public movingCell(Vector2i position, Vector2i size, Item item) {
			super(position, size, item);
		}

		public void render(Graphics g) {
//			g.setColor(color);
//			g.fillRect( position.x - size.x >> 1, position.y - size.y >> 1, size.x, size.y);
			if(super.getItem() != null) 
				g.drawImage(getItem().getIcom(), position.x - (size.x >> 1), position.y - (size.y >> 1), size.x, size.y, null);
			
		}
	}

}
