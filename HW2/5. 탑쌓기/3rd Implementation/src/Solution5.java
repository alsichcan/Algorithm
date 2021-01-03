import java.util.ArrayList;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.TreeMap;

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
    static int[] h = new int[max_n], d = new int[max_n - 1];
    static int Answer;
    static TreeMap<Integer, Integer> TowerGroup_A;
    static TreeMap<Integer, Integer> TowerGroup_B;

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
        for (int test_case = 1; test_case <= 6; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 블록의 개수와 최대 높이를 각각 n, H에 읽어들입니다.
			   그리고 각 블록의 높이를 h[0], h[1], ... , h[n-1]에 읽어들입니다.
			   다음 각 블록에 파인 구멍의 깊이를 d[0], d[1], ... , d[n-2]에 읽어들입니다.
			 */
            stk = new StringTokenizer(br.readLine());
            n = Integer.parseInt(stk.nextToken());
            H = Integer.parseInt(stk.nextToken());
            stk = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                h[i] = Integer.parseInt(stk.nextToken());
            }
            stk = new StringTokenizer(br.readLine());
            for (int i = 0; i < n - 1; i++) {
                d[i] = Integer.parseInt(stk.nextToken());
            }

            /////////////////////////////////////////////////////////////////////////////////////////////

            Answer = 0;

            TowerGroup_A = new TreeMap<>();
            TowerGroup_B = new TreeMap<>();

            TowerGroup_A.put(h[0], 1);

            for (int block = 1; block < n; block++) {

                TreeMap<Integer, Integer> TowerSubGroup_A = new TreeMap<>(TowerGroup_A.headMap(H - (h[block] - d[block - 1]), true));
                TreeMap<Integer, Integer> TowerSubGroup_B = new TreeMap<>(TowerGroup_B.headMap(H - h[block], true));

                for (int height : TowerGroup_A.keySet()) {
                    if (TowerGroup_B.containsKey(height))
                        TowerGroup_B.replace(height, TowerGroup_B.get(height) + TowerGroup_A.get(height));
                    else
                        TowerGroup_B.put(height, TowerGroup_A.get(height));
                } // end for

                TowerGroup_A.clear();

                TowerGroup_A.put(h[block], 1);

                for (int height : TowerSubGroup_A.keySet()) {
                    int newHeight = height + h[block] - d[block - 1];

                    if (TowerGroup_A.containsKey(newHeight))
                        TowerGroup_A.replace(newHeight, (TowerGroup_A.get(newHeight) + TowerSubGroup_A.get(height)) % 1000000);
                    else
                        TowerGroup_A.put(newHeight, TowerSubGroup_A.get(height) % 1000000);
                } // end for

                for (int height : TowerSubGroup_B.keySet()) {
                    int newHeight = height + h[block];

                    if (TowerGroup_A.containsKey(newHeight))
                        TowerGroup_A.replace(newHeight, (TowerGroup_A.get(newHeight) + TowerSubGroup_B.get(height)) % 1000000);
                    else
                        TowerGroup_A.put(newHeight, TowerSubGroup_B.get(height) % 1000000);
                } // end for
            } // end for

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            for (int height : TowerGroup_A.keySet()) {
                if (TowerGroup_B.containsKey(height))
                    TowerGroup_B.replace(height, TowerGroup_B.get(height) + TowerGroup_A.get(height));
                else
                    TowerGroup_B.put(height, TowerGroup_A.get(height));
            } // end for


            for (int tower : TowerGroup_B.keySet()) {
                if (TowerGroup_B.get(tower) < 0)
                    System.out.println(TowerGroup_B.get(tower));
                Answer += TowerGroup_B.get(tower);
                Answer %= 1000000;
            }

            //////////////////////////////////////////////////////////////////////////////////////////////////////////

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