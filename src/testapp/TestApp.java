package testapp;

import applicationapi.Application;
import applicationapi.Device;
import applicationapi.graphics.Canvas;
import applicationapi.graphics.Color;
import applicationapi.graphics.Screen;
import applicationapi.graphics.Sprite;
import applicationapi.graphics.SpriteBuilder;
import applicationapi.graphics.SpriteFactory;


/**
 *
 * @author tog
 */
public class TestApp implements Application
{
    private Sprite ball;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private float pixelsPrMilliSecond;
    
    @Override
    public boolean initialize(Device dev)
    {
        Screen screen = dev.getScreen();
        if(screen == null) return false;
        this.width = screen.getWidth();
        this.height = screen.getHeight();
        this.posY = height / 2;
        this.pixelsPrMilliSecond = ((float) width) / 5000.0f;
        int r = height / 24;
        int r2 = r*r;
        int s = r+r+1;
        SpriteFactory sf = screen.getSpriteFactory();
        SpriteBuilder bld = sf.newSprite(s, s);
        Color black = sf.newColor(0, 0, 0, 1);
        Color white = sf.newColor(1, 1, 1, 0);
        for(int x = 0; x < s; ++x)
        {
            for(int y = 0; y < s; ++y)
            {
                int dx = x - r;
                int dy = y - r;
                int dx2 = dx * dx;
                int dy2 = dy * dy;
                int dist2 = dx2 + dy2;
                if(dist2 >= r2)
                {
                    bld.setPixel(x, y, white);
                }
                else
                {
                    bld.setPixel(x, y, black);
                }
            }
        }
        bld.setAnchor(r, r);
        ball = bld.build();
        return true;
    }

    @Override
    public boolean update(long time)
    {
        posX = (int) (time * pixelsPrMilliSecond);
        return (posX < width); 
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawSprite(posX, posY, ball);
    }

    @Override
    public void destroy()
    {
        //Do nothing
    }

    
    
}
