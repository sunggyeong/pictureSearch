package project05;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pictureSearch.Stuff;

public class StuffPanel extends JPanel {
    private static final long serialVersionUID = 3811037325320199526L;
    private JLabel stuffTypeLabel = new JLabel("Type");
    private JLabel stuffNameLabel = new JLabel("Name");
    private JLabel stuffTagsLabel = new JLabel("Tags");
    
    private JTextField stuffTypeField=new JTextField(20);
    private JTextField stuffNameField=new JTextField(20);
    private JTextField stuffTagsField=new JTextField(20);

    public StuffPanel() {
        setLayout(new GridLayout(3, 2, 1, 0));
        add(stuffTypeLabel);
        add(stuffTypeField);
        add(stuffNameLabel);
        add(stuffNameField);
        add(stuffTagsLabel);
        add(stuffTagsField);
        setBorder(new LineBorder(Color.BLACK, 1));
    }

    public StuffPanel(Stuff stuff) {
        setLayout(new GridLayout(3, 2, 1, 0));
        add(stuffTypeLabel);
        add(new JTextField(stuff.getStuffType()));
        add(stuffNameLabel);
        add(new JTextField(stuff.getStuffName()));
        add(stuffTagsLabel);
        add(new JTextField(stuff.getStuffTags()));
        setBorder(new LineBorder(Color.BLACK, 1));
    }
    
    public JTextField getStuffTypeField() {
    	return stuffTypeField;
    }
    public JTextField getStuffNameField() {
    	return stuffNameField;
    }
    public JTextField getStuffTagsField() {
    	return stuffTagsField;
    }
    
}

