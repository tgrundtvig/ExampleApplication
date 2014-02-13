package exampleapplication;

import applicationapi.Application;
import applicationapi.Device;
import applicationapi.graphics.Canvas;
import applicationapi.graphics.Color;
import applicationapi.graphics.Screen;
import applicationapi.graphics.Sprite;
import applicationapi.graphics.SpriteBuilder;
import applicationapi.graphics.SpriteFactory;
import applicationapi.input.keyboard.Key;
import applicationapi.input.keyboard.KeyEvent;
import applicationapi.input.keyboard.KeyboardListener;


/**
 *
 * @author tog
 */
public class ExampleApplication implements Application, KeyboardListener
{
    private boolean keyLeftDown;
    private boolean keyRightDown;
    private Sprite ball;
    private float fPos;
    private int posY;
    private int width;
    private int height;
    private float movePrMilliSecond;
    private long lastTime;
    
    @Override
    public boolean initialize(Device dev)
    {
        this.fPos = 0.5f;
        this.keyLeftDown = false;
        this.keyRightDown = false;
        dev.getKeyboard().addKeyboardListener(this);
        Screen screen = dev.getScreen();
        if(screen == null) return false;
        this.width = screen.getWidth();
        this.height = screen.getHeight();
        this.posY = height / 2;
        this.movePrMilliSecond = 1.0f / 5000.0f;
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
        long deltaTime = time - lastTime;
        lastTime = time;
        float fdx; 
        if(keyLeftDown && !keyRightDown) fdx = deltaTime * -movePrMilliSecond;
        else if(keyRightDown && !keyLeftDown) fdx = deltaTime * movePrMilliSecond;
        else fdx = 0.0f;
        fPos += fdx;
        return (fPos >= 0.0f && fPos <= 1.0f); 
    }

    @Override
    public void draw(Canvas canvas)
    {
        int posX = (int) (fPos * width);
        canvas.drawSprite(posX, posY, ball);
    }

    @Override
    public void destroy()
    {
        //Do nothing
    }

    @Override
    public void onKeyPress(KeyEvent ke)
    {
        if(ke.getKey() == Key.VK_LEFT) keyLeftDown = true;
        else if(ke.getKey() == Key.VK_RIGHT) keyRightDown = true;
    }

    @Override
    public void onKeyRelease(KeyEvent ke)
    {
        if(ke.getKey() == Key.VK_LEFT) keyLeftDown = false;
        if(ke.getKey() == Key.VK_RIGHT) keyRightDown = false;
    }

    
    
}
