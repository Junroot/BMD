import java.awt.Image;
import java.util.Random;

import loot.ImageResourceManager;
import loot.graphics.DrawableObject;

public class Block extends DrawableObject
{
	int status; // 0 : 없음, 1 : 부숴질 수 있음, 2 : 부숴질 수 없음
	int block_x, block_y; // 게임 상의 블럭의 위치
	int width, height;
	double px, py;
	ImageResourceManager images;
	public Block(int x, int y,int width, int height, int status, ImageResourceManager images)
	{
		super(x*56 + 136, y*56 + 24 - (height - 56), width, height);
		if(status == 0)	this.image = images.GetImage("not_exist");
		else if(status == 1)
		{
			Random r = new Random();
			int sel = r.nextInt(3);
			if (sel == 0)	this.image = images.GetImage("removable1");
			else if (sel == 1)	this.image = images.GetImage("removable2");
			else if (sel == 2)	this.image = images.GetImage("removable3");
		}
		else if(status == 2) this.image = images.GetImage("not_removable");
		this.status = status;
		this.width = width;
		this.height = height;
		this.images = images;
		block_x = x;
		block_y = y;
		px = x*56 + 136;
		py = y*56 + 24;
	}

	public void ChangeStatus(int status)
	{
		if(status == 0)	this.image = images.GetImage("not_exist");
		else if(status == 1) this.image = images.GetImage("removable");
		else if(status == 2) this.image = images.GetImage("not_removable");
		this.status = status;
	}
}
