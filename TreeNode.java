import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;
/**
 */
public class TreeNode
{
	private Integer data;
	TreeNode leftTree;
	TreeNode rightTree;

	public TreeNode()
	{
	}
	public Integer getData()
	{
		return data;
	}
	public void setData(Integer data)
	{
		this.data = data;
	}
	public TreeNode getLeftTree()
	{
		return leftTree;
	}
	public void setLeftTree(TreeNode leftTree)
	{
		this.leftTree = leftTree;
	}
	public TreeNode getRightTree()
	{
		return rightTree;
	}
	public void setRightTree(TreeNode rightTree)
	{
		this.rightTree = rightTree;
	}

	public static void main(String[] arg)
	{

		List<Integer> proderList = new LinkedList<>();
		proderList.add(1);
		proderList.add(2);
		proderList.add(4);
		proderList.add(5);
		proderList.add(3);
		proderList.add(6);
		proderList.add(7);

		List<Integer> inorderList = new LinkedList<>();
		inorderList.add(4);
		inorderList.add(2);
		inorderList.add(5);
		inorderList.add(1);
		inorderList.add(6);
		inorderList.add(3);
		inorderList.add(7);

		/*proderList.add(1);
		proderList.add(2);
		proderList.add(3);

		inorderList.add(1);
		inorderList.add(2);
		inorderList.add(3);*/

		Map<Integer, Integer> posMap = generatePosMap(proderList,
				inorderList);
		TreeNode t = buildTree(inorderList,
				0,
				inorderList.size() - 1,
				posMap);
		preorderTraversal(t);
		levelorderTraversal(t);
		Map<Integer, Integer> m = new LinkedHashMap<>();
		preOrderVerSum(t,
				0,
				m);
		System.out.println("Inverse Level traversal");
		inverseOrderTraversal(t);
		System.out.println("Zigzag traversal");
		zigZagTraversal(t);
		System.out.println(isMirror(t,
				t));

	}

	public static boolean isMirror(TreeNode t1,
			TreeNode t2)
	{
		boolean retVal = false;

		if (t1 != null && t2 != null)
		{
			boolean isSameData = t1.getData() == t2.getData();
			boolean isLeftMirror = isMirror(t1.getLeftTree(),
					t2.getLeftTree());
			boolean isRightMirror = isMirror(t1.getRightTree(),
					t2.getRightTree());
			retVal = isSameData && isLeftMirror && isRightMirror;
		}
		else if (t1 == null && t2 == null)
		{
			retVal = true;
		}
		else if (t1 == null || t2 == null)
		{
			retVal = false;
		}

		return retVal;
	}

	public static void zigZagTraversal(TreeNode t)
	{
		Queue<TreeNode> queue = new LinkedList<>();
		if (t != null)
		{
			queue.add(t);
			TreeNode t100 = new TreeNode();
			t100.setData(100);

			TreeNode t200 = new TreeNode();
			t200.setData(200);

			queue.add(t100);
			List<Integer> list = new LinkedList<>();
			while (!queue.isEmpty())
			{
				TreeNode thead = queue.remove();
				TreeNode tnextHead = queue.peek();
				list.add(thead.getData());

				if (thead.getData() == 100 && tnextHead != null)
				{
					queue.add(t200);
				}
				else if (thead.getData() == 200 && tnextHead != null)
				{
					queue.add(t100);
				}
				else
				{
					if (thead.getLeftTree() != null)
					{
						queue.add(thead.getLeftTree());
					}
					if (thead.getRightTree() != null)
					{
						queue.add(thead.getRightTree());
					}

				}

			}

			if (list.size() > 0)
			{

				for (int i = 0; i < list.size(); i++)
				{
					while(list.get(i) != 100 && i < list.size())
					{
						System.out.println("Testing=="+list.get(i));
						i++;
					}
					i++;

					Stack<Integer> stack = new Stack();
					while (list.get(i) != 200 && i < list.size())
					{
						stack.push(list.get(i));
						i++;
					}

					while (!stack.empty())
					{
						System.out.println(stack.pop());
					}

				}

			}
		}

	}

	public static void preorderTraversal(TreeNode t)
	{
		if (t != null)
		{
			System.out.println(t.getData());
			preorderTraversal(t.getLeftTree());
			preorderTraversal(t.getRightTree());
		}
	}



	public static void preOrderVerSum(TreeNode t,
			Integer i,
			Map<Integer, Integer> verSum)
	{
		if (t != null)
		{

			preOrderVerSum(t.getLeftTree(),
					i - 1,
					verSum);

			int sum = verSum.get(i) != null ? verSum.get(i) + t.getData() : t.getData();
			verSum.put(i,
					sum);

			if (t.getRightTree() != null)
			{

				preOrderVerSum(t.getRightTree(),
						i + 1,
						verSum);
			}
		}

	}

	public static void inverseOrderTraversal(TreeNode t)
	{
		if (t != null)
		{
			Queue<TreeNode> queue = new LinkedList<>();
			queue.add(t);
			while (!queue.isEmpty())
			{
				TreeNode t1 = queue.remove();
				System.out.println(t1.getData());
				if (t1.getRightTree() != null)
				{
					queue.add(t1.getRightTree());
				}
				if (t1.getLeftTree() != null)
				{
					queue.add(t1.getLeftTree());
				}
			}

		}
	}

	public static Map<Integer, Integer> generatePosMap(List<Integer> preorder,
			List<Integer> inorder)
	{
		Map<Integer, Integer> posMap = new LinkedHashMap<>();
		for (int i = 0; i < inorder.size(); i++)
		{
			for (int j = 0; j < preorder.size(); j++)
			{
				if (preorder.get(j) == inorder.get(i))
				{
					posMap.put(inorder.get(i),
							j);
				}
			}
		}
		return posMap;
	}

	public static TreeNode buildTree(List<Integer> inorder,
			int start,
			int end,
			Map<Integer, Integer> posMap)
	{

		TreeNode t = new TreeNode();

		if (start == end)
		{
			t.setData(inorder.get(start));
			t.setLeftTree(null);
			t.setRightTree(null);
		}
		else if (start > end)
		{
			return null;
		}
		else
		{
			Integer rootDataPos = findRootPos(inorder,
					start,
					end,
					posMap);

			t.setData(inorder.get(rootDataPos));
			t.setLeftTree(buildTree(inorder,
					start,
					rootDataPos - 1,
					posMap));
			t.setRightTree(buildTree(inorder,
					rootDataPos + 1,
					end,
					posMap));
		}

		return t;

	}

	public static Integer findRootPos(List<Integer> inorder,
			int start,
			int end,
			Map<Integer, Integer> posMap)
	{
		int small = 1000;
		Integer psnInInorder = null;
		for (int i = start; i <= end; i++)
		{
			int posInMap = posMap.get(inorder.get(i));
			if (posInMap < small)
			{
				small = posInMap;
				psnInInorder = i;
			}
		}
		return psnInInorder;
	}

	public static void levelorderTraversal(TreeNode t)
	{
		if (t != null)
		{
			Queue<TreeNode> queue = new LinkedList<>();
			queue.add(t);

			while (!queue.isEmpty())
			{
				TreeNode temp = queue.remove();
				if (temp != null)
				{
					System.out.println(temp.getData());
					if (temp.getLeftTree() != null)
					{
						queue.add(temp.getLeftTree());
					}

					if (temp.getRightTree() != null)
					{
						queue.add(temp.getRightTree());
					}
				}
			}
		}

	}

}




