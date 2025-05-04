package model;

import java.util.Random;

public class MapGenerator {

    static int[][] map;
    //经典地图
    public static int[][] generatorMap1(){
        map= new int[][]{
                {2, 2, 2, 2, 1},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {2, 2, 2, 2, 1},
        };
        return map;
    }

    //测试用的
    public static int[][] generatorMapTest(){
        map= new int[][]{
                {2, 2, 2, 2, 1},
                {3, 0, 0, 0, 1},
                {3, 0, 0, 4, 4},
                {0, 0, 0, 4, 4}
        };
        return map;
    }

    //随机产生一个地图，滑块2个数2-4 滑块1个数2-4；
    public static int[][] generatorMapRandom(){
        Random random=new Random();
        //滑块4的位置
        int rowOf4,colOf4;
        do {
             rowOf4= random.nextInt(3);
             colOf4= random.nextInt(4);
        }while (rowOf4==1&&colOf4==3);
        map=new int[4][5];
        map[rowOf4][colOf4]=4;
        map[rowOf4+1][colOf4]=4;
        map[rowOf4][colOf4+1]=4;
        map[rowOf4+1][colOf4+1]=4;
        //滑块3的位置
        int rowOf3= random.nextInt(3);
        int colOf3= random.nextInt(5);
        while (!(map[rowOf3][colOf3]==0&&map[rowOf3+1][colOf3]==0)){
            rowOf3= random.nextInt(3);
            colOf3= random.nextInt(5);
        }
        map[rowOf3][colOf3]=3;
        map[rowOf3+1][colOf3]=3;
        //滑块2的数量&位置
        int num2;
        num2= random.nextInt(3)+2;//2~4
        int rowOf2= random.nextInt(4);
        int colOf2= random.nextInt(4);
        for(int i=0;i<num2;i++){
            while (!(map[rowOf2][colOf2]==0&&map[rowOf2][colOf2+1]==0)){
                rowOf2= random.nextInt(4);
                colOf2= random.nextInt(4);
            }
            map[rowOf2][colOf2]=2;
            map[rowOf2][colOf2+1]=2;
        }
        //滑块1的数量和位置
        int num1=random.nextInt(3)+2;//2~4
        int rowOf1= random.nextInt(4);
        int colOf1= random.nextInt(5);
        for(int i=0;i<num1;i++){
            while (map[rowOf1][colOf1]!=0){
                rowOf1= random.nextInt(4);
                colOf1= random.nextInt(5);
            }
            map[rowOf1][colOf1]=1;
        }
        return map;
    }

}
