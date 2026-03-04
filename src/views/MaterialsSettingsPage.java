package views;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.ComputerPackHandler;
import handlers.MaterialHandler;
import handlers.MaterialTypeHandler;
import models.ComputerPack;
import models.Material;
import models.MaterialType;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MaterialsSettingsPage extends JPanel {

    private final JPanel settingsPanel;
    private final JButton addPackButton;
    private final JButton addTypeButton;
    private final ComputerPackHandler computerPackHandler;
    private final MaterialHandler materialHandler;
    private final MaterialTypeHandler materialTypeHandler;

    public MaterialsSettingsPage(){
        setPreferredSize(GlobalUtilities.getCommonHomePageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        setBackground(AppColors.WHITE_COLOR);

        computerPackHandler = new ComputerPackHandler();
        materialHandler = new MaterialHandler();
        materialTypeHandler = new MaterialTypeHandler();

        JPanel mainPanel = new JPanel(new BorderLayout(0 , 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        mainPanel.setOpaque(false);

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout(0 , 10));
        settingsPanel.setOpaque(true);
        settingsPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
        settingsPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.STRICT_WHITE_COLOR , 1 , 20));

        addPackButton = new JButton("+ Nouveau pack");
        setUpAddButton(addPackButton);
        addTypeButton = new JButton("+ Nouveau type");
        setUpAddButton(addTypeButton);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);

        JPanel titleWrapper = new JPanel(new BorderLayout(15, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));
        JPanel accentBar = new JPanel();
        accentBar.setPreferredSize(new Dimension(4, 30));
        accentBar.setBackground(AppColors.MEDIUM_BASE_BLUE_COLOR);
        accentBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, AppColors.WHITE_COLOR));
        JLabel panelTitle = new JLabel("Packs et Types de vos assets");
        panelTitle.setFont(GlobalUtilities.getBoldAppFont(24f));
        panelTitle.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);

        titleWrapper.add(accentBar, BorderLayout.WEST);
        titleWrapper.add(panelTitle, BorderLayout.CENTER);

        headerPanel.add(titleWrapper, BorderLayout.WEST);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.add(addPackButton);
        actionsPanel.add(addTypeButton);
        JPanel verticalCenterWrapper = new JPanel(new GridBagLayout());
        verticalCenterWrapper.setOpaque(false);
        verticalCenterWrapper.add(actionsPanel);
        headerPanel.add(verticalCenterWrapper, BorderLayout.EAST);

        headerPanel.setBackground(AppColors.WHITE_COLOR);
        headerPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));

        settingsPanel.add(headerPanel , BorderLayout.NORTH);

        JPanel topPanel = createTitlePanel();

        mainPanel.add(topPanel , BorderLayout.NORTH);
        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(mainPanel, gbc);
    }

    private void setUpAddButton(JButton button){
        button.setFont(GlobalUtilities.getBoldAppFont(14f));
        button.setForeground(AppColors.WHITE_COLOR);
        button.setBackground(AppColors.BASE_BLUE_COLOR);
        Dimension buttonDimension = new Dimension(190 , 40);
        button.setPreferredSize(buttonDimension);
        button.setMinimumSize(buttonDimension);
        button.setCursor(GlobalUtilities.getHandCursor());
    }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout(10 , 0));
        titlePanel.setOpaque(true);
        titlePanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));
        titlePanel.setBackground(AppColors.DARK_BLUE_COLOR);

        JLabel title = new JLabel("Paramétrage matériel");
        title.setFont(GlobalUtilities.getBoldAppFont(28f));
        title.setForeground(AppColors.STRICT_WHITE_COLOR);

        JLabel subTitle = new JLabel("Gérez les types et packs de vos matériaux");
        subTitle.setFont(GlobalUtilities.getBoldSmallAppFont());
        subTitle.setForeground(AppColors.WHITE_COLOR);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.add(title , BorderLayout.NORTH);
        titleWrapper.add(subTitle , BorderLayout.SOUTH);

        titlePanel.add(titleWrapper , BorderLayout.WEST);

        return titlePanel;
    }

    public void updateData(List<ComputerPack> packs , List<MaterialType> types){

        JPanel bodyContainer = new JPanel();
        bodyContainer.setLayout(new BoxLayout(bodyContainer, BoxLayout.Y_AXIS));
        bodyContainer.setOpaque(false);
        bodyContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        Component centerComponent = ((BorderLayout) settingsPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) settingsPanel.remove(centerComponent);

        bodyContainer.add(createPacksPanel(packs));
        bodyContainer.add(Box.createVerticalStrut(10));
        bodyContainer.add(createTypesPanel(types));
        bodyContainer.add(Box.createVerticalStrut(10));
        bodyContainer.add(createMaterialsInPacksPanel(packs));

        JScrollPane bodyWrapper = new JScrollPane(bodyContainer);
        bodyWrapper.setBorder(null);
        bodyWrapper.getVerticalScrollBar().setUnitIncrement(16);
        bodyWrapper.getHorizontalScrollBar().setUnitIncrement(16);
        bodyWrapper.setOpaque(false);
        bodyWrapper.getViewport().setOpaque(false);

        settingsPanel.add(bodyWrapper , BorderLayout.CENTER);

        settingsPanel.revalidate();
        settingsPanel.repaint();

    }

    private JPanel createPacksPanel(List<ComputerPack> packs){
        JPanel container = new JPanel(new BorderLayout(0, 20));
        container.setOpaque(false);

        int count = computerPackHandler.getComputerPacksCountInDB();
        String str = (count > 1) ? "packs" : "pack";
        JLabel label = new JLabel("• Packs enregistrés ( " + count + " " + str + " )");
        label.setFont(GlobalUtilities.getBoldAppFont(20f));
        label.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
        container.add(label, BorderLayout.NORTH);

        JPanel packsWrapper = new JPanel();
        packsWrapper.setLayout(new BoxLayout(packsWrapper , BoxLayout.Y_AXIS));
        packsWrapper.setOpaque(false);

        for (ComputerPack pack : packs) {
            JPanel packPanel = new JPanel(new BorderLayout(0 , 5));
            packPanel.setOpaque(false);

            JLabel packName = new JLabel(pack.getName());
            packName.setFont(GlobalUtilities.getBoldAppFont(16f));
            packName.setForeground(AppColors.MEDIUM_BASE_BLUE_COLOR);
            packName.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.MEDIUM_BASE_BLUE_COLOR , 1 , 20));

            JLabel packDescription = new JLabel(pack.getDescription());
            packDescription.setFont(GlobalUtilities.getBoldAppFont(13f));
            packDescription.setOpaque(false);
            packDescription.setForeground(AppColors.DARK_BLUE_COLOR);

            packPanel.add(packName , BorderLayout.WEST);
            packPanel.add(packDescription , BorderLayout.SOUTH);

            packsWrapper.add(packPanel);
            packsWrapper.add(Box.createVerticalStrut(20));
        }

        container.add(packsWrapper, BorderLayout.CENTER);

        return container;
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

    private JPanel createTypesPanel(List<MaterialType> types){
        JPanel container = new JPanel(new BorderLayout(0, 20));
        container.setOpaque(false);

        int count = materialTypeHandler.getMaterialTypesCountInDB();
        String str = (count > 1) ? "types" : "type";
        JLabel label = new JLabel("• Types de matériaux enregistrés ( " + count + " " + str + " )");
        label.setFont(GlobalUtilities.getBoldAppFont(20f));
        label.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
        container.add(label, BorderLayout.NORTH);

        JPanel typesWrapper = new JPanel();
        typesWrapper.setLayout(new BoxLayout(typesWrapper , BoxLayout.Y_AXIS));
        typesWrapper.setOpaque(false);
        typesWrapper.setBorder(new EmptyBorder(5, 60, 5, 8));

        for (MaterialType type : types) {
            typesWrapper.add(createBadge(type.getName()));
            typesWrapper.add(Box.createVerticalStrut(12));
        }

        container.add(typesWrapper, BorderLayout.WEST);

        return container;
    }

    private JPanel createMaterialsInPacksPanel(List<ComputerPack> packs){
        JPanel container = new JPanel(new BorderLayout(0, 20));
        container.setOpaque(false);

        JLabel label = new JLabel("• Détails des composition des packs");
        label.setFont(GlobalUtilities.getBoldAppFont(20));
        label.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
        container.add(label, BorderLayout.NORTH);

        JPanel listWrapper = new JPanel();
        listWrapper.setLayout(new BoxLayout(listWrapper , BoxLayout.Y_AXIS));
        listWrapper.setOpaque(false);

        for (ComputerPack pack : packs) {
            listWrapper.add(createMaterialsPanelByPack(pack));
            listWrapper.add(Box.createVerticalStrut(12));
        }

        container.add(listWrapper, BorderLayout.CENTER);

        return container;
    }

    private JPanel createMaterialsPanelByPack(ComputerPack pack){
        JPanel mainPanel = new JPanel(new BorderLayout(0 , 15));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(AppColors.WHITE_COLOR);
        mainPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        List<Material> materialsInPack = materialHandler.getMaterialsByComputerPackID(pack.getId());

        JLabel title = new JLabel(pack.getName());
        title.setFont(GlobalUtilities.getBoldAppFont(18f));

        int count = materialsInPack.size();
        String str = (count > 1) ? "matériaux" : "matériel";
        JLabel countLabel = new JLabel(count + " " + str);
        countLabel.setForeground(Color.GRAY);
        countLabel.setFont(GlobalUtilities.getBoldSmallAppFont());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(countLabel, BorderLayout.EAST);

        JPanel materialsPanel = new JPanel();
        materialsPanel.setLayout(new BoxLayout(materialsPanel , BoxLayout.Y_AXIS));
        materialsPanel.setOpaque(false);

        if (materialsInPack.isEmpty()){
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            JLabel emptyLabel = new JLabel("Aucun matériel enregistré dans ce pack...");
            emptyPanel.setOpaque(false);
            emptyLabel.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
            emptyLabel.setFont(GlobalUtilities.getBoldAppFont(16f));
            emptyPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
            emptyPanel.add(emptyLabel);
            materialsPanel.add(emptyPanel, BorderLayout.CENTER);
        }else {
            for (Material material : materialsInPack) {
                JPanel materialLayout = new JPanel();
                materialLayout.setLayout(new BoxLayout(materialLayout, BoxLayout.X_AXIS));
                materialLayout.setOpaque(false);
                materialLayout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel id = new JLabel("MAT-" + material.getId());
                id.setForeground(AppColors.DARK_BLUE_COLOR);
                id.setFont(GlobalUtilities.getBoldSmallAppFont());
                id.setPreferredSize(new Dimension(80, 20));
                id.setMinimumSize(new Dimension(80, 20));

                JLabel name = new JLabel(material.getDesignation() + " • " + material.getSpecification());
                name.setForeground(AppColors.MEDIUM_BASE_BLUE_COLOR);
                name.setFont(GlobalUtilities.getBoldAppFont(14f));

                JLabel date = new JLabel("Acquis le " + material.getAcquisitionDate());
                date.setForeground(AppColors.GRAY_COLOR);
                date.setFont(GlobalUtilities.getBoldAppFont(13f));
                date.setHorizontalAlignment(SwingConstants.RIGHT);

                materialLayout.add(id);
                materialLayout.add(Box.createHorizontalStrut(15));
                materialLayout.add(name);
                materialLayout.add(Box.createHorizontalGlue());
                materialLayout.add(date);

                materialsPanel.add(materialLayout);

                materialsPanel.add(Box.createVerticalStrut(10));
            }
        }

        mainPanel.add(header , BorderLayout.NORTH);
        mainPanel.add(materialsPanel , BorderLayout.CENTER);

        return mainPanel;
    }

    public JButton getAddPackButton() { return addPackButton; }

    public JButton getAddTypeButton() { return addTypeButton; }

}