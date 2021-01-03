import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution1 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution1.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output1.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution1

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution1
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution1   // 0.5초 수행
       timeout 1 java Solution1     // 1초 수행
 */

class Solution1 {
	static final int MAX_N = 10000;
	static final int MAX_E = 500000;

	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int[] Answer = new int[MAX_N+1];

    static LinkedList<Edges>[] Vertexes;
    static LinkedList<Integer> sortedVertexes;
    static boolean[] visited;

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
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 시작점, 끝점, 가중치를 각각 U[i], V[i], W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());

			// 수행시간 : O(N)
			Vertexes = new LinkedList[N+1];
			for(int i = 0; i < Vertexes.length ; i++)
			    Vertexes[i] = new LinkedList<>();


			// 수행시간 : O(E)
			for (int i = 0; i < E; i++) {
				stk = new StringTokenizer(br.readLine());
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());


				Vertexes[U[i]].add(new Edges(V[i], W[i]));
			}

			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 출력해야 할 전체 N-1개의 수를 Answer[0], Answer[1], ... , Answer[N-2]에 저장하는 것을 가정하였습니다.
			 */

			Arrays.fill(Answer, Integer.MAX_VALUE);
			Answer[1] = 0;

			sortedVertexes = new LinkedList<>();
			visited = new boolean[N+1];

			// <쉽게 배우는 알고리즘> p.327의 위상 정렬 알고리즘 : O(N+E)
            for (int vertex = 1; vertex <= N ; vertex++){
                if(!visited[vertex])
                    DFS_TS(vertex);
            }

            // 최단 경로 구하기 : O(N+E)
            for(int vertex : sortedVertexes){

                if(Answer[vertex] == Integer.MAX_VALUE)
                    continue;

                for(Edges edge : Vertexes[vertex]){

                    if(Answer[vertex] + edge.weight < Answer[edge.dest])
                        Answer[edge.dest] = Answer[vertex] + edge.weight;
                }
            }

			/////////////////////////////////////////////////////////////////////////////////////////////

			// 수행시간 : O(N)
			// output1.txt로 답안을 출력합니다. 문자 'X'를 출력하기 위해 필요에 따라 아래 코드를 수정하셔도 됩니다.
			pw.print("#" + test_case);
			for (int i = 2; i < N+1; i++) {
			    if(Answer[i] == Integer.MAX_VALUE)
				    pw.print(" " + "X");
			    else
			        pw.print(" " + Answer[i]);
			}
			pw.println();
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

	public static void DFS_TS(int vertex){
	    visited[vertex] = true;
	    for(Edges edge : Vertexes[vertex]){
	        if(!visited[edge.dest])
	            DFS_TS(edge.dest);
        }
        sortedVertexes.add(0, vertex );
    }

}


class Edges{
    public int dest;
    public int weight;

    public Edges(int dest, int weight){
        this.dest = dest;
        this.weight = weight;
    }
}