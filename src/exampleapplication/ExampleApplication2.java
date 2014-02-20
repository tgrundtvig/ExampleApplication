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
import applicationapi.input.keyboard.KeyPressedEvent;
import applicationapi.input.keyboard.KeyReleasedEvent;
import applicationapi.input.keyboard.KeyboardListener;
import applicationapi.input.mouse.MouseButton;
import applicationapi.input.mouse.MousePressedEvent;
import applicationapi.input.mouse.MouseListener;
import applicationapi.input.mouse.MouseMovedEvent;
import applicationapi.input.mouse.MouseReleasedEvent;
import applicationapi.input.mouse.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tog
 */
public class ExampleApplication2 implements Application, KeyboardListener, MouseListener
{
    private Sprite dot;
    private int mouseX;
    private int mouseY;
    private boolean quit;
    
    @Override
    public boolean initialize(Device dev)
    {
        this.quit = false;
        
        this.mouseX = 0;
        this.mouseY = 0;
        dev.getKeyboard().addKeyboardListener(this);
        dev.getMouse().addMouseListener(this);
        Screen screen = dev.getScreen();
        if(screen == null) return false;
        SpriteFactory sf = screen.getSpriteFactory();
        Color black = sf.newColor(0, 0, 0, 1);
        int dotSize = 10;
        SpriteBuilder bld = sf.newSprite(dotSize, dotSize);
        for(int y = 0; y < dotSize; ++y)
        {
            for(int x = 0; x < dotSize; ++x)
            {
                bld.setPixel(x, y, black);
            }
        }
        bld.setAnchor(dotSize/2, dotSize/2);
        dot = bld.build();
        return true;
    }

    @Override
    public boolean update(long time)
    {
        return !quit; 
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawSprite(mouseX, mouseY, dot);
    }

    @Override
    public void destroy()
    {
        //Do nothing
    }

    @Override
    public void onKeyPressed(KeyPressedEvent e)
    {
        if(e.getKey() == Key.VK_ESC) quit = true;
    }

    @Override
    public void onKeyReleased(KeyReleasedEvent e)
    {
        //Do nothing...
    }

    @Override
    public void onMouseMoved(MouseMovedEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void onMousePressed(MousePressedEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void onMouseReleased(MouseReleasedEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void onMouseWheel(MouseWheelEvent e)
    {
        //Do nothing
    }
    
}
