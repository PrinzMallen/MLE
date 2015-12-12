/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PingPong;

/**
 *
 * @author Alexander
 */
public class Status {
    public int xBallPos;
    public int yBallPos;
    public int xBallSpeed;
    public int yBallSpeed;
    public int xSchlägerPos;

    public Status(int xBallPos, int yBallPos, int xBallSpeed, int yBallSpeed, int xSchlägerPos) {
        this.xBallPos = xBallPos;
        this.yBallPos = yBallPos;
        this.xBallSpeed = xBallSpeed;
        this.yBallSpeed = yBallSpeed;
        this.xSchlägerPos = xSchlägerPos;
        if(xBallSpeed<0){
            this.xBallSpeed=0;
        }
        if(yBallSpeed<0){
            this.yBallSpeed=0;
        }
    }

    
    
    
    
    
}
