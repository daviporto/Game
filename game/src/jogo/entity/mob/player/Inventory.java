package jogo.entity.mob.player;

import jogo.graphics.ui.UIInventoryCell;
import jogo.graphics.ui.UIManager;
import jogo.graphics.ui.UIPanel;
import jogo.util.Vector2i;

public class Inventory {
	private UIManager ui;
	private UIInventoryCell[] cells = new UIInventoryCell[6];
	private UIPanel inventoryPanel;

	public Inventory(UIManager ui) {
		this.ui = ui;

		inventoryPanel = new UIPanel(new Vector2i(170, 440), new Vector2i(400, 80));
		inventoryPanel.setColor(0x10606060);

		cells[0] = new UIInventoryCell(new Vector2i(10, 10), UIInventoryCell.DEFAULTSIZE);
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
			inventoryPanel.addComponent(c);

		ui.addPanel(inventoryPanel);
	}

}
