import java.awt.Image;

import loot.graphics.DrawableObject;


public class Boooom extends DrawableObject
{
	int block_x, block_y;
	double spawntime;
	public Boooom(int x,int y, Image image,double timeStamp)
	{
		super(x*56 + 136, y*56 + 24, 56, 56, image);
		block_x = x;
		block_y = y;
		spawntime = timeStamp;
	}
}
