package views;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.BreakDownHandler;
import handlers.MaterialHandler;
import models.BreakdownOccurrence;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BreakDownsPage extends JPanel {

    private final JPanel breakDownsPanel;
    private final JTextField searchField;
    private final JButton addButton;
    private final BreakDownHandler breakDownHandler;
    private final MaterialHandler materialHandler;

    public BreakDownsPage(){
        setPreferredSize(GlobalUtilities.getCommonHomePageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        setBackground(AppColors.WHITE_COLOR);

        breakDownHandler = new BreakDownHandler();
        materialHandler = new MaterialHandler();

        JPanel mainPanel = new JPanel(new BorderLayout(0 , 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        mainPanel.setOpaque(false);

        breakDownsPanel = new JPanel();
        breakDownsPanel.setLayout(new BorderLayout());
        breakDownsPanel.setOpaque(true);
        breakDownsPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
        breakDownsPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.STRICT_WHITE_COLOR , 1 , 20));


        addButton = new JButton("+ Déclarer une panne");
        addButton.setFont(GlobalUtilities.getBoldAppFont(14f));
        addButton.setForeground(AppColors.WHITE_COLOR);
        addButton.setBackground(AppColors.BASE_BLUE_COLOR);
        Dimension buttonDimension = new Dimension(190 , 40);
        addButton.setPreferredSize(buttonDimension);
        addButton.setMinimumSize(buttonDimension);
        addButton.setCursor(GlobalUtilities.getHandCursor());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher une panne ...");
        Dimension fieldDimension = new Dimension(500 , 5);
        searchField.setPreferredSize(fieldDimension);
        searchField.setMargin(new Insets(1 , 10 , 1 , 0));
        searchField.setFont(GlobalUtilities.getMediumAppFont());
        headerPanel.add(searchField, BorderLayout.WEST);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.add(addButton);
        headerPanel.add(actionsPanel, BorderLayout.EAST);
        headerPanel.add(Box.createVerticalStrut(12) , BorderLayout.SOUTH);

        breakDownsPanel.add(headerPanel , BorderLayout.NORTH);

        JPanel topPanel = createTitlePanel();

        mainPanel.add(topPanel , BorderLayout.NORTH);
        mainPanel.add(breakDownsPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(mainPanel, gbc);

    }

    public void updateData(List<BreakdownOccurrence> breakdownOccurrences) {
        JPanel breakDownsContainer = new JPanel();
        breakDownsContainer.setLayout(new BoxLayout(breakDownsContainer, BoxLayout.Y_AXIS));
        breakDownsContainer.setOpaque(false);
        breakDownsContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        Component centerComponent = ((BorderLayout) breakDownsPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) breakDownsPanel.remove(centerComponent);

        if (breakdownOccurrences.isEmpty()){
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            JLabel emptyLabel = new JLabel("Aucune panne enregistrée...");
            emptyLabel.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
            emptyLabel.setFont(GlobalUtilities.getBoldAppFont(20f));
            emptyPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
            emptyPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
            emptyPanel.add(emptyLabel);

            breakDownsPanel.add(emptyPanel, BorderLayout.CENTER);
        }else{
            for (BreakdownOccurrence breakdownOccurrence : breakdownOccurrences) {
                breakDownsContainer.add(createBreakDownCard(breakdownOccurrence));
                breakDownsContainer.add(Box.createVerticalStrut(12));
            }
            breakDownsContainer.add(Box.createVerticalGlue());

            JScrollPane scrollPane = new JScrollPane(breakDownsContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            breakDownsPanel.add(scrollPane, BorderLayout.CENTER);
        }

        breakDownsPanel.revalidate();
        breakDownsPanel.repaint();
    }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout(10 , 0));
        titlePanel.setOpaque(true);
        titlePanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));
        titlePanel.setBackground(AppColors.DARK_BLUE_COLOR);

        JLabel title = new JLabel("Pannes");
        title.setFont(GlobalUtilities.getBoldAppFont(28f));
        title.setForeground(AppColors.STRICT_WHITE_COLOR);

        JLabel subTitle = new JLabel("Visualisez l'historique des pannes de vos assets informatiques");
        subTitle.setFont(GlobalUtilities.getBoldSmallAppFont());
        subTitle.setForeground(AppColors.WHITE_COLOR);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.add(title , BorderLayout.NORTH);
        titleWrapper.add(subTitle , BorderLayout.SOUTH);

        titlePanel.add(titleWrapper , BorderLayout.WEST);

        return titlePanel;
    }

    private JPanel createBadge(String text) {
        JLabel label = new JLabel(text);
        label.setFont(GlobalUtilities.getBoldSmallAppFont());

        JPanel badge = new JPanel(new GridBagLayout());
        badge.setBorder(new EmptyBorder(5, 8, 5, 8));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc: 12");

        badge.setBackground(AppColors.SEMI_LIGHT_GRAY_COLOR);
        label.setForeground(AppColors.GRAY_COLOR);

        badge.add(label);

        return badge;
    }

    private JPanel createBreakDownCard(BreakdownOccurrence breakdownOccurrence) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { card.setBackground(AppColors.LIGHT_GRAY_COLOR); }

            @Override
            public void mouseExited(MouseEvent e) { card.setBackground(AppColors.WHITE_COLOR); }
        });

        JLabel breakdownLabel = new JLabel("Panne : ");
        breakdownLabel.setFont(GlobalUtilities.getBoldSmallAppFont());

        JPanel breakdown = createBadge(breakDownHandler.getBreakDownByID(breakdownOccurrence.getBreakdownID()).getDescription());
        JPanel breakdownPanel = new JPanel(new GridBagLayout());
        breakdownPanel.setOpaque(false);
        breakdownPanel.add(breakdown);

        JLabel materialLabel = new JLabel("Matériel : ");
        materialLabel.setFont(GlobalUtilities.getBoldSmallAppFont());

        JPanel material = createBadge(materialHandler.getMaterialByID(breakdownOccurrence.getMaterialID()).getDesignation());
        JPanel materialPanel = new JPanel(new GridBagLayout());
        materialPanel.setOpaque(false);
        materialPanel.add(material);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT , 7 , 0);

        JPanel breakdownWrapper = new JPanel(flowLayout);
        breakdownWrapper.add(breakdownLabel);
        breakdownWrapper.add(breakdownPanel);

        JPanel materialWrapper = new JPanel(flowLayout);
        materialWrapper.add(materialLabel);
        materialWrapper.add(materialPanel);

        breakdownWrapper.setOpaque(false);
        materialWrapper.setOpaque(false);

        JLabel date = new JLabel("Date de la panne : " + breakdownOccurrence.getBreakdownDate());
        date.setFont(GlobalUtilities.getBoldSmallAppFont());
        date.setForeground(AppColors.GRAY_COLOR);

        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setOpaque(false);

        horizontalPanel.add(breakdownWrapper);
        horizontalPanel.add(Box.createHorizontalStrut(10));
        horizontalPanel.add(materialWrapper);
        horizontalPanel.add(Box.createVerticalGlue());
        horizontalPanel.add(date);

        card.add(horizontalPanel , BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE , 100));

        return card;
    }

    public JTextField getSearchField() { return searchField; }

    public JButton getAddButton() { return addButton; }

}