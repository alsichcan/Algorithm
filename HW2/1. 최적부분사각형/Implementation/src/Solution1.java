import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution1 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution1.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution1

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution1
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution1   // 0.5초 수행
       timeout 1 java Solution1     // 1초 수행
 */

class Solution1 {
	static final int max_n = 200;

	static int n;
	static int[][] A = new int[max_n][max_n];
	static int Answer;

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input1.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output1.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 파일에서 읽어옵니다.
			   먼저 정사각 행렬의 크기를 n에 읽어들입니다.
			   그리고 첫 번째 행에 쓰여진 n개의 숫자를 차례로 A[0][0], A[0][1], ... , A[0][n-1]에 읽어들입니다.
			   마찬가지로 두 번째 행에 쓰여진 n개의 숫자를 차례로 A[1][0], A[1][1], ... , A[1][n-1]에 읽어들이고,
			   세 번째 행에 쓰여진 n개의 숫자를 차례로 A[2][0], A[2][1], ... , A[2][n-1]에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < n; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}

			// 최소값으로 초기화한다.
			Answer = Integer.MIN_VALUE;

			// 수행시간 : O(n^2)
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
				    //홀수인 경우 음수처리
					if(A[i][j] % 2 == 1)
						A[i][j] *= -1;
					//누적값이 되도록 한다.
					if(i >= 1)
						A[i][j] += A[i-1][j];
				}
			}

			// 수행시간 : O(n^3)
			for(int row_excluded = -1; row_excluded < n; row_excluded++){
			    for(int row = row_excluded + 1; row < n; row++){

			        int rowRectMax = Integer.MIN_VALUE;
			        int current_rect = Integer.MIN_VALUE;

			        for(int column = 0; column < n; column++){
			            int new_rect;

			            if(row_excluded == -1)
                            new_rect = A[row][column];
			            else
			                new_rect = A[row][column] - A[row_excluded][column];

			            if(column == 0) {
                            current_rect = new_rect;
                            rowRectMax = new_rect;
                        }
			            else {
                            current_rect = Math.max(new_rect, current_rect + new_rect);
                            rowRectMax = Math.max(rowRectMax, current_rect);
                        }
                    }
                    Answer = Math.max(Answer, rowRectMax);
                }
            }

			// output1.txt로 답안을 출력합니다.
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

