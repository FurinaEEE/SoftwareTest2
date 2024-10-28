package runner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.image.BufferedImage;


public class MainRunnerGUI {
    private JFrame frame;
    private JComboBox<String> demoSelector;
    private JButton runButton;
    private JButton showDiagramsButton;
    private JTextArea inputTextArea;
    private String inputFilePath = "D:\\txt\\input.txt";
    private String outputFilePath = "D:\\txt\\output.txt";

    public MainRunnerGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("经典软件体系结构教学软件");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());

        demoSelector = new JComboBox<>(new String[]{"主程序-子程序软件体系结构", "面向对象软件体系结构", "事件系统软件体系结构", "管道-过滤软件体系结构"});
        frame.add(demoSelector);

        inputTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        frame.add(scrollPane);

        runButton = new JButton("运行");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedDemo = demoSelector.getSelectedIndex();
                saveInputToFile();
                runDemo(selectedDemo + 1);
            }
        });
        frame.add(runButton);

        showDiagramsButton = new JButton("查看原理");
        showDiagramsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedDemo = demoSelector.getSelectedIndex();
                showDiagrams(selectedDemo + 1);
            }
        });
        frame.add(showDiagramsButton);

        frame.setVisible(true);
    }

    private void saveInputToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
            writer.write(inputTextArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runDemo(final int choice) {
        Thread demoThread = new Thread(() -> {
            try {
                PrintStream out = new PrintStream(new FileOutputStream(outputFilePath));
                System.setOut(out);

                switch (choice) {
                    case 1:
                        demo1.demo1.executeMain(new String[]{});
                        break;
                    case 2:
                        demo2.Main.executeMain(new String[]{});
                        break;
                    case 3:
                        demo3.Main.executeMain(new String[]{});
                        break;
                    case 4:
                        demo4.Main.executeMain(new String[]{});
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }

                System.setOut(System.out);

                SwingUtilities.invokeLater(this::displayOutput);

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        demoThread.start();
    }

    private void displayOutput() {
        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame outputFrame = new JFrame("Output");
        outputFrame.setSize(400, 300);
        outputFrame.setLayout(new BorderLayout());
        outputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea outputArea = new JTextArea(content);
        outputArea.setEditable(false);
        outputFrame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        outputFrame.setVisible(true);
    }

    private void showDiagrams(int demoNumber) {
        String principleDiagramPath = "D:\\picture\\y" + demoNumber + ".png";
        String codeDiagramPath = "D:\\picture\\d" + demoNumber + ".png";

        JFrame diagramsFrame = new JFrame("Diagrams for Demo " + demoNumber);
        diagramsFrame.setSize(2400, 1800);
        diagramsFrame.setLayout(new GridBagLayout());
        diagramsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // 添加到剩余空间
        gbc.fill = GridBagConstraints.BOTH; // 填充水平和垂直空间
        gbc.insets = new Insets(10, 10, 10, 10); // 设置外边距

        // 加载并调整原理图大小
        ImageIcon principleDiagramIcon = new ImageIcon(principleDiagramPath);
        Image principleImage = principleDiagramIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledPrincipleDiagramIcon = new ImageIcon(principleImage);
        JLabel principleDiagramLabel = new JLabel(scaledPrincipleDiagramIcon);

        // 加载并调整代码图大小
        ImageIcon codeDiagramIcon = new ImageIcon(codeDiagramPath);
        Image codeImage = codeDiagramIcon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledCodeDiagramIcon = new ImageIcon(codeImage);
        JLabel codeDiagramLabel = new JLabel(scaledCodeDiagramIcon);

        // 添加原理图
        gbc.gridx = 0; // 第一列
        gbc.gridy = 0; // 第一行
        diagramsFrame.add(principleDiagramLabel, gbc);

        // 添加原理图标签
        JLabel principleDiagramLabelText = new JLabel("原理图");
        gbc.gridx = 1; // 第二列
        gbc.gridy = 0; // 第一行
        diagramsFrame.add(principleDiagramLabelText, gbc);

        // 添加代码图
        gbc.gridx = 0; // 第一列
        gbc.gridy = 1; // 第二行
        diagramsFrame.add(codeDiagramLabel, gbc);

        // 添加代码图标签
        JLabel codeDiagramLabelText = new JLabel("代码图");
        gbc.gridx = 1; // 第二列
        gbc.gridy = 1; // 第二行
        diagramsFrame.add(codeDiagramLabelText, gbc);

        diagramsFrame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainRunnerGUI());
    }
}