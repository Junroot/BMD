import java.awt.Image;

import loot.graphics.DrawableObject;


public class Bomb extends DrawableObject
{
	int dir;
	int block_x, block_y;
	long spawntime;
	double vel_x, vel_y;
	
	public Bomb(int x,int y, Image image, int dir)
	{
		super(x*56 + 136, y*56 + 24, 56, 56, image);
		vel_x = 0;
		vel_y = 0;
		block_x = x;
		block_y = y;
		this.dir = dir;
	}
}
