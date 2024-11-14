import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = this.right = null;
    }
}

class HuffmanTree {
    private Map<Character, String> huffmanCodeMap = new HashMap<>();

    // Build Huffman Tree
    public HuffmanNode buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> a.frequency - b.frequency);

        // Insert all characters of the frequency map into the priority queue
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            int sum = left.frequency + right.frequency;
            HuffmanNode node = new HuffmanNode('\0', sum);
            node.left = left;
            node.right = right;
            pq.offer(node);
        }

        return pq.peek();
    }

    // Generate Huffman Codes by traversing the tree
    public void generateCodes(HuffmanNode root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmanCodeMap.put(root.character, code);
        }
        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }

    // Encode a given word
    public String encode(String word) {
        StringBuilder encodedString = new StringBuilder();
        for (char ch : word.toCharArray()) {
            encodedString.append(huffmanCodeMap.get(ch));
        }
        return encodedString.toString();
    }

    public Map<Character, String> getHuffmanCodeMap() {
        return huffmanCodeMap;
    }
}

public class HuffmanCoding {
    public static void main(String[] args) {
        String text = "WINTER IS NEARLY GONE. TIME FLOWS ON TO A SPRING OF LITTLE HOPE.";
        
        // Calculate frequency of each character
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        HuffmanTree huffmanTree = new HuffmanTree();
        HuffmanNode root = huffmanTree.buildTree(freqMap);
        huffmanTree.generateCodes(root, "");

        // Output Huffman codes for each character
        System.out.println("Huffman Codes: " + huffmanTree.getHuffmanCodeMap());

        // Encode the word "ARAGORN"
        String encodedARAGORN = huffmanTree.encode("ARAGORN");
        System.out.println("Encoded 'ARAGORN': " + encodedARAGORN);
    }
}