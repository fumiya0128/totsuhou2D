package test;
import java.util.ArrayList;
import java.util.List;
public class housouhou2 {
	public static void main(String[] args){
		//double pointlist[][]= {{1,0},{2,4},{3,3.5},{4,2}};
		//double pointlist[][]= {{1,1},{2,0},{3,1},{1.5,1.5},{2,1}};
		//double pointlist[][]= {{0,0},{1,0},{0,1},{1,1},{0.5,0.5}};
		//double pointlist[][]= {{1,1},{2,1},{3,3},{1,2},{0,1.5},{2,2}};
		double pointlist[][]= {{3,1},{3,2},{3,3},{2,1},{2,2},{2,3},{1,1},{1,2},{1,3}};
	    List<Integer> list = new ArrayList<Integer>();
		int miny=0;//y最小の点番号(左下)(最初の原点)
		int maxy=0;//y最大の点番号(右上)
		int genten=pointlist.length;//更新される原点
		boolean round=true;//判定方向(trueならx正基準)
		for(int i=0;i<pointlist.length;i++) {
			if((pointlist[i][1]<pointlist[miny][1])||(pointlist[i][1]==pointlist[miny][1]&&pointlist[i][0]<pointlist[miny][0])) {
				miny=i;//y最小の更新
			}
		}
		for(int i=0;i<pointlist.length;i++) {
			if((pointlist[i][1]>pointlist[maxy][1])||(pointlist[i][1]==pointlist[maxy][1]&&pointlist[i][0]>pointlist[maxy][0])) {
				maxy=i;//y最大の更新
			}
		}
		System.out.println(miny);
		System.out.println(maxy);
		double pointdata[][]=new double[pointlist.length][5];//[最初からの象限,傾き,更新後象限,傾き,検索除外]
		for(int i=0;i<pointlist.length;i++) {//最初の原点からの象限と傾きを求める
			if(miny!=i) {
				double xsa=pointlist[i][0]-pointlist[miny][0];
				double ysa=pointlist[i][1]-pointlist[miny][1];
				if(ysa>0) {
					if(xsa>0) {//第1象限
						pointdata[i][0]=1;
						pointdata[i][1]=Math.abs(ysa/xsa);
					}else if(xsa<0) {//第2象限
						pointdata[i][0]=2;
						pointdata[i][1]=Math.abs(xsa/ysa);
					}else if(xsa==0) {
						pointdata[i][0]=2;
						pointdata[i][1]=0;
					}
				}else if(ysa<0) {
					if(xsa<0) {//第3象限
						pointdata[i][0]=3;
						pointdata[i][1]=Math.abs(ysa/xsa);
					}else if(xsa>0) {//第4象限
						pointdata[i][0]=4;
						pointdata[i][1]=Math.abs(xsa/ysa);
					}else if(xsa==0) {
						pointdata[i][0]=4;
						pointdata[i][1]=0;
					}
				}else if(ysa==0) {
					if(xsa>0) {
						pointdata[i][0]=1;
					    pointdata[i][1]=0;
					}else {
						pointdata[i][0]=3;
					    pointdata[i][1]=0;
					}
				}
			}
			pointdata[i][4]=1;
		}
		int nextgenten=-1;//次の原点
		while(genten!=miny) {//再度原点を見つけるまで
			if(genten==pointlist.length) {
				genten=miny;//原点の初期設定
			}
			list.add(genten);//リストに追加
			double minsyougen=5;//最小象限
			double minkatamuki=0;//最小傾き
			for(int i=0;i<pointlist.length;i++) {//次の点を探す
				if(round==true&&pointdata[i][4]==1&&i!=genten) {
					double xsa=pointlist[i][0]-pointlist[genten][0];
					double ysa=pointlist[i][1]-pointlist[genten][1];
					if(ysa>0) {
						if(xsa>0) {//第1象限
							pointdata[i][2]=1;
							pointdata[i][3]=Math.abs(ysa/xsa);
						}else if(xsa<0) {//第2象限
							pointdata[i][2]=2;
							pointdata[i][3]=Math.abs(xsa/ysa);
						}else if(xsa==0) {//π/2
							pointdata[i][2]=2;
							pointdata[i][3]=0;
						}
					}else if(ysa<0) {
						if(xsa<0) {//第3象限
							pointdata[i][2]=3;
							pointdata[i][3]=Math.abs(ysa/xsa);
						}else if(xsa>0) {//第4象限
							pointdata[i][2]=4;
							pointdata[i][3]=Math.abs(xsa/ysa);
						}else if(xsa==0) {//3π/2
							pointdata[i][2]=4;
							pointdata[i][3]=0;
						}
					}else if(ysa==0) {
						if(xsa>0) {//0
							pointdata[i][2]=1;
						    pointdata[i][3]=0;
						}else {//π
							pointdata[i][2]=3;
						    pointdata[i][3]=0;
						}
					}
					if(minsyougen>pointdata[i][2]) {//最小象限かの判断（最初は必ず通る）
						minsyougen=pointdata[i][2];
						minkatamuki=pointdata[i][3];
						nextgenten=i;
					}else if(minsyougen==pointdata[i][2]) {
						if(minkatamuki>pointdata[i][3]) {//最小象限内での傾き比較
							minkatamuki=pointdata[i][3];
							nextgenten=i;
						}else if(minkatamuki==pointdata[i][3]) {//最小象限内かつ傾き等しい時の比較
							if(Math.abs(pointlist[nextgenten][1]-pointlist[genten][1])>Math.abs(ysa)||Math.abs(pointlist[nextgenten][0]-pointlist[genten][0])>Math.abs(xsa)) {
								minkatamuki=pointdata[i][3];
								nextgenten=i;
							}
						}
					}
				}
				if(round==false&&pointdata[i][4]==1&&i!=genten) {
					double xsa=pointlist[i][0]-pointlist[genten][0];
					double ysa=pointlist[i][1]-pointlist[genten][1];
					if(ysa>0) {
						if(xsa>0) {//第1象限
							pointdata[i][2]=3;
							pointdata[i][3]=Math.abs(ysa/xsa);
						}else if(xsa<0) {//第2象限
							pointdata[i][2]=4;
							pointdata[i][3]=Math.abs(xsa/ysa);
						}else if(xsa==0) {
							pointdata[i][2]=4;
							pointdata[i][3]=0;
						}
					}else if(ysa<0) {
						if(xsa<0) {//第3象限
							pointdata[i][2]=1;
							pointdata[i][3]=Math.abs(ysa/xsa);
						}else if(xsa>0) {//第4象限
							pointdata[i][2]=2;
							pointdata[i][3]=Math.abs(xsa/ysa);
						}else if(xsa==0) {
							pointdata[i][2]=2;
							pointdata[i][3]=0;
						}
					}else if(ysa==0) {
						if(xsa>0) {
							pointdata[i][2]=3;
						    pointdata[i][3]=0;
						}else {
							pointdata[i][2]=1;
						    pointdata[i][3]=0;
						}
					}
					if(minsyougen>pointdata[i][2]) {
						minsyougen=pointdata[i][2];
						minkatamuki=pointdata[i][3];
						nextgenten=i;
					}else if(minsyougen==pointdata[i][2]) {
						if(minkatamuki>pointdata[i][3]) {
							minkatamuki=pointdata[i][3];
							nextgenten=i;
						}else if(minkatamuki==pointdata[i][3]) {
							if(Math.abs(pointlist[nextgenten][1]-pointlist[genten][1])>Math.abs(ysa)||Math.abs(pointlist[nextgenten][0]-pointlist[genten][0])>Math.abs(xsa)) {
								minkatamuki=pointdata[i][3];
								nextgenten=i;
							}
						}
					}
				}
				//System.out.println(" "+genten+" "+minsyougen+" "+minkatamuki);
			}
			pointdata[nextgenten][4]=0;//次の原点を探索済みにする
			for(int i=0;i<pointlist.length;i++) {//最適化(最初の原点からの傾きで判定)
				if(i!=miny&&pointdata[i][4]==1&&(pointdata[nextgenten][0]>pointdata[i][0]||(pointdata[nextgenten][0]==pointdata[i][0]&&pointdata[nextgenten][1]>pointdata[i][1]))) {
					pointdata[i][4]=0;
				}
			}
			genten=nextgenten;//原点の更新
			for(int ii=0;ii<pointlist.length;ii++) {//確認用
				if(pointdata[ii][4]==1){
					System.out.print(ii+" ");
					System.out.print(round+" ");
					System.out.print(pointdata[ii][0]+" ");
					System.out.print(pointdata[ii][1]+" ");
					System.out.print(pointdata[ii][2]+" ");
					System.out.print(pointdata[ii][3]+" ");
					System.out.print(pointdata[ii][4]+" ");
					System.out.println(genten);
				}				
			}
			if(maxy==genten) {//右上に到達したら向き変更
				round=false;
			}
			System.out.println();
		}
		System.out.println(list);
	}
}
