import java.awt.Image;

import loot.ImageResourceManager;
import loot.graphics.DrawableObject;


public class Item extends DrawableObject
{
	int block_x, block_y;
	int type; // 0 : ∆¯≈∫ ¡ı∞°, 1 : ∆¯≈∫ ¡Ÿ±‚ ¡ı∞°, 2 : ¥Ÿ¿ÃæÓ∆Æ
	Image[] imgs;
	public Item(int x, int y, int type, ImageResourceManager images)
	{
		super(x*56 + 136, y*56 + 24, 56, 56);
		block_x = x;
		block_y = y;
		imgs = new Image[4];
		if (type == 0)
		{
			image = images.GetImage("coin1");
			imgs[0] = images.GetImage("coin1");
			imgs[1] = images.GetImage("coin2");
			imgs[2] = images.GetImage("coin3");
			imgs[3] = images.GetImage("coin4");
		}
		else if (type == 1)
		{
			image = images.GetImage("coupon1");
			imgs[0] = images.GetImage("coupon1");
			imgs[1] = images.GetImage("coupon2");
			imgs[2] = images.GetImage("coupon3");
			imgs[3] = images.GetImage("coupon4");
		}
		else if (type == 2)
		{
			{
				image = images.GetImage("diet1");
				imgs[0] = images.GetImage("diet1");
				imgs[1] = images.GetImage("diet2");
				imgs[2] = images.GetImage("diet3");
				imgs[3] = images.GetImage("diet4");
			}
		}
		this.type = type;
	}

	public void Update (double timeStamp)
	{
		image = imgs[((int)(timeStamp/250))%4];
	}
}
