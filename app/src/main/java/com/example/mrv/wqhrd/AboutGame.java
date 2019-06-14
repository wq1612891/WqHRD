package com.example.mrv.wqhrd;

import android.widget.AbsoluteLayout;
import android.widget.TextView;

public class AboutGame {
    static int phoneWidth;//手机宽度
    static int phoneHeight;//手机高度
    static final int yNum = 5;//棋盘垂直方向正方形块的数量
    static final int xNum = 4;//棋盘水平方向正方形块的数量
    static final int chessNum = 10;
    static final int[] chessSize={1,2, 1,2, 1,2, 1,2, 2,1, 2,2, 1,1, 1,1, 1,1, 1,1};//棋子的大小（相对于基础块的大小）
    static int round = 1;//等级
    static final int[][] chessPosition={
            {0,0, 3,0, 0,2, 3,2, 1,2, 1,0, 1,3, 2,3, 0,4, 3,4},
            {3,0, 2,1, 1,3, 0,2, 2,3, 0,0, 2,0, 1,2, 3,2, 2,4},
            {2,0, 1,2, 0,3, 3,3, 2,2, 0,0, 0,2, 1,4, 2,3, 2,4},
            {2,0, 3,0, 2,2, 3,2, 0,1, 0,2, 0,4, 1,4, 2,4, 3,4},
            {2,1, 3,1, 2,3, 3,3, 0,3, 0,1, 0,0, 1,0, 2,0, 3,0}
    };//每一关卡对应的棋子坐标
    static TextView[] chess=new TextView[chessNum];//所有棋子
    static int step = 0;//步数
    static TextView showStep;
    static int squareSize;//"元方块"的大小
}
