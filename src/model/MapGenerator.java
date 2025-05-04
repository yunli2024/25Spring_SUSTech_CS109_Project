package model;

import java.util.Random;

public class MapGenerator {

    static int[][] map;
    //经典地图
    public static int[][] generatorMap1(){
        map= new int[][]{//横刀立马
                {2, 2, 2, 2, 1},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {2, 2, 2, 2, 1},
        };
        return map;
    }
    public static int[][] generatorMap2(){
        map= new int[][]{//指挥若定
                {2, 2, 1, 2, 2},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {2, 2, 1, 2, 2},
        };
        return map;
    }
    public static int[][] generatorMap3(){
        map= new int[][]{//兵分三路
                {1, 2, 2, 2, 2},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {1, 2, 2, 2, 2},
        };
        return map;
    }
    public static int[][] generatorMap4(){
        map= new int[][]{//桃花园中
                {1, 2, 2, 1, 0},
                {4, 4, 2, 2, 3},
                {4, 4, 2, 2, 3},
                {1, 2, 2, 1, 0},
        };
        return map;
    }

    public static int[][] generatorMap(){
        Random random=new Random();
        int randMap=random.nextInt(4)+1;
        if (randMap==1) return generatorMap1();
        else if(randMap==2) return generatorMap2();
        else if(randMap==3) return generatorMap3();
        else  return generatorMap4();
    }

    //简化版经典地图
    public static int[][] easyGeneratorMap1(){
        map= new int[][]{//横刀立马简化版
                {2, 2, 0, 0, 1},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {2, 2, 0, 0, 1},
        };
        return map;
    }
    public static int[][] easyGeneratorMap2(){
        map= new int[][]{//指挥若定简化版
                {2, 2, 1, 0, 0},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {2, 2, 1, 0, 0},
        };
        return map;
    }
    public static int[][] easyGeneratorMap3(){
        map= new int[][]{//兵分三路简化版
                {1, 0, 0, 2, 2},
                {4, 4, 3, 1, 0},
                {4, 4, 3, 1, 0},
                {1, 0, 0, 2, 2},
        };
        return map;
    }
    public static int[][] easyGeneratorMap4(){
        map= new int[][]{//桃花园中简化版
                {1, 2, 2, 1, 0},
                {4, 4, 2, 0, 3},
                {4, 4, 2, 0, 3},
                {1, 2, 2, 1, 0},
        };
        return map;
    }

    public static int[][] easyGeneratorMap(){
        Random random=new Random();
        int randMap=random.nextInt(4)+1;
        if (randMap==1) return easyGeneratorMap1();
        else if(randMap==2) return easyGeneratorMap2();
        else if(randMap==3) return easyGeneratorMap3();
        else  return easyGeneratorMap4();
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
