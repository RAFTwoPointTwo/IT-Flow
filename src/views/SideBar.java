package views;

import com.formdev.flatlaf.FlatClientProperties;
import utilities.AppColors;
import utilities.GlobalUtilities;
import utilities.PageNames;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SideBar extends JPanel {

    private final JButton dashboardButton = new JButton("Dashboard");
    private final JButton materialsButton = new JButton("Matériaux");
    private final JButton assignmentsButton = new JButton("Affectations");
    private final JButton breakdownsButton = new JButton("Pannes");
    private final JButton interventionsButton = new JButton("Interventions");
    private final JButton materialsSettingsButton = new JButton("Paramétrage matériel");
    private final JLabel adminLabel;

    public SideBar() {

        setLayout(new BorderLayout(0 , 30));
        setBackground(AppColors.DARK_BLUE_COLOR);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, AppColors.GRAY_COLOR));

        JLabel logoLabel = new JLabel("I.T Flow", SwingConstants.CENTER);
        logoLabel.setFont(GlobalUtilities.getBoldLargeAppFont());
        logoLabel.setForeground(AppColors.STRICT_WHITE_COLOR);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel logoWrapper = new JPanel(new GridBagLayout());
        logoWrapper.setOpaque(false);
        logoWrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.WHITE_COLOR));
        logoWrapper.add(logoLabel);

        initButtonsCommands();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        List.of(dashboardButton, materialsButton, assignmentsButton, breakdownsButton, interventionsButton, materialsSettingsButton)
                .forEach(button -> {
                    setUpButtonStyle(button);
                    buttonsPanel.add(button);
                    buttonsPanel.add(Box.createVerticalStrut(10));
                });

        JPanel adminPanel = new JPanel(new GridBagLayout());
        adminPanel.setOpaque(true);
        adminPanel.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        adminPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 15));
        adminLabel = new JLabel();
        adminLabel.setForeground(AppColors.WHITE_COLOR);
        adminLabel.setFont(GlobalUtilities.getBoldMediumAppFont());
        adminPanel.add(adminLabel);
        JPanel wrappedAdminPanel = new JPanel(new GridBagLayout());
        wrappedAdminPanel.setBorder(BorderFactory.createEmptyBorder(50 , 0 , 20 , 0));
        wrappedAdminPanel.setOpaque(false);
        wrappedAdminPanel.add(adminPanel);
        JPanel adminPanelWrapper = new JPanel(new GridBagLayout());
        adminPanelWrapper.setOpaque(false);
        adminPanelWrapper.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppColors.WHITE_COLOR));
        adminPanelWrapper.add(wrappedAdminPanel);

        add(logoWrapper, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(adminPanelWrapper , BorderLayout.SOUTH);

        setPreferredSize(new Dimension(230 , 0));
    }


    private void initButtonsCommands(){
        dashboardButton.setActionCommand(PageNames.DASHBOARD_PAGE);
        materialsButton.setActionCommand(PageNames.MATERIALS_PAGE);
        assignmentsButton.setActionCommand(PageNames.ASSIGNMENTS_PAGE);
        breakdownsButton.setActionCommand(PageNames.BREAKDOWNS_PAGE);
        interventionsButton.setActionCommand(PageNames.INTERVENTIONS_PAGE);
        materialsSettingsButton.setActionCommand(PageNames.MATERIALS_SETTINGS_PAGE);
    }

    private void setUpButtonStyle(JButton button) {
        button.setFont(GlobalUtilities.getBoldMediumAppFont());
        button.setForeground(AppColors.WHITE_COLOR);
        button.setBackground(AppColors.DARK_BLUE_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(GlobalUtilities.getHandCursor());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 10, 0, 0));
        button.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
    }

    public JButton getDashboardButton() { return dashboardButton; }

    public JButton getMaterialsButton() { return materialsButton; }

    public JButton getAssignmentsButton() { return assignmentsButton; }

    public JButton getBreakdownsButton() { return breakdownsButton; }

    public JButton getInterventionsButton() { return interventionsButton; }

    public JButton getMaterialsSettingsButton() { return materialsSettingsButton; }

    public void insertAdminName(String adminName) { adminLabel.setText("ADMIN : " + adminName.trim()); }

}