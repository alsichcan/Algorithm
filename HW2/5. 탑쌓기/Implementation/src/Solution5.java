import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution5 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution5.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output5.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution5

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution5
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution5   // 0.5초 수행
       timeout 1 java Solution5     // 1초 수행
 */

class Solution5 {
	static final int max_n = 1000;
	static int n, H;
	static int[] h = new int[max_n], d = new int[max_n-1];
	static int Answer;
	static double TowerCount;
	static HashSet<Integer> TotalBlock;
	static HashSet<HashSet<Integer>> TowerLog;
	static TreeMap<Integer, ArrayList<HashSet<Integer>>> TowerGroup;

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input5.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output5.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input5.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output5.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 블록의 개수와 최대 높이를 각각 n, H에 읽어들입니다.
			   그리고 각 블록의 높이를 h[0], h[1], ... , h[n-1]에 읽어들입니다.
			   다음 각 블록에 파인 구멍의 깊이를 d[0], d[1], ... , d[n-2]에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken()); H = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
				h[i] = Integer.parseInt(stk.nextToken());
			}
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n-1; i++) {
				d[i] = Integer.parseInt(stk.nextToken());
			}


			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
            TowerCount = 0;
			TowerLog = new HashSet<>();
			TowerGroup = new TreeMap<>();
			TotalBlock = new HashSet<>();
			for(int i=0; i < n; i++) TotalBlock.add(i);


            for(int i = 0; i< n; i++) {
                ArrayList<HashSet<Integer>> list = new ArrayList<>();
                HashSet<Integer> set = new HashSet<>();
                set.add(i);
                list.add(set);

                TowerGroup.put(h[i],list);
                TowerLog.add(set);
                TowerCount++;
            }

            while(!TowerGroup.isEmpty()){

                int height = TowerGroup.firstKey();
                HashSet<Integer> Blocks = TowerGroup.get(height).get(0);

                HashSet<Integer> ExBlocks = new HashSet<>(TotalBlock);
                ExBlocks.removeAll(Blocks);

                for(int newBlock : ExBlocks) {
                    HashSet<Integer> newBlocks = new HashSet<>(Blocks);
                    newBlocks.add(newBlock);

                    if(TowerLog.contains(newBlocks))
                        continue;

                    boolean hasUpper = (newBlock != n - 1) && newBlocks.contains(newBlock + 1);
                    boolean hasLower = (newBlock != 0) && newBlocks.contains(newBlock - 1);

                    int newHeight;
                    if (hasLower && hasUpper)
                        newHeight = height + h[newBlock] - d[newBlock - 1] - d[newBlock];
                    else if (hasLower && !hasUpper)
                        newHeight = height + h[newBlock] - d[newBlock - 1];
                    else if (!hasLower && hasUpper)
                        newHeight = height + h[newBlock] - d[newBlock];
                    else
                        newHeight = height + h[newBlock];

                    if (newHeight < H) {
                        if(TowerGroup.containsKey(newHeight))
                            TowerGroup.get(newHeight).add(newBlocks);
                        else {
                            ArrayList<HashSet<Integer>> list = new ArrayList<>();
                            list.add(newBlocks);
                            TowerGroup.put(newHeight, list);
                        }
                        TowerLog.add(newBlocks);
                        TowerCount++;
                    } else if(newHeight == H) {
                        TowerLog.add(newBlocks);
                        TowerCount++;
                    }
                }

                if(TowerGroup.get(height).size() == 1)
                    TowerGroup.remove(height);
                else
                    TowerGroup.get(height).remove(0);
            } // end while





            Answer = (int) TowerCount % 1000000;

            System.out.println("Answer : " + Answer);

            // output5.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
			pw.flush();
		}

		br.close();
		pw.close();
	}
}