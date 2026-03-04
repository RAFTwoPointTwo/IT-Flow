package views;

import com.formdev.flatlaf.FlatClientProperties;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPage extends JPanel {

    private final JPanel body;
    private final FlowLayout flowLayout;

    public DashboardPage(){
        setPreferredSize(GlobalUtilities.getCommonHomePageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        setBackground(AppColors.WHITE_COLOR);

        JPanel mainPanel = new JPanel(new BorderLayout(0 , 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        mainPanel.setOpaque(false);

        flowLayout = new FlowLayout(FlowLayout.LEFT , 7 , 0);

        JPanel titlePanel = createTitlePanel();

        body = new JPanel(new GridLayout(0, 1, 10, 20));
        body.setOpaque(true);
        body.setBackground(AppColors.STRICT_WHITE_COLOR);
        body.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.STRICT_WHITE_COLOR , 1 , 20));

        mainPanel.add(titlePanel , BorderLayout.NORTH);

        JScrollPane bodyWrapper = new JScrollPane(body);
        bodyWrapper.setBorder(null);
        bodyWrapper.getVerticalScrollBar().setUnitIncrement(16);
        bodyWrapper.setOpaque(false);
        bodyWrapper.getViewport().setOpaque(false);

        mainPanel.add(bodyWrapper, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(mainPanel, gbc);

    }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout(10 , 0));
        titlePanel.setOpaque(true);
        titlePanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));
        titlePanel.setBackground(AppColors.DARK_BLUE_COLOR);

        JLabel title = new JLabel("Dashboard");
        title.setFont(GlobalUtilities.getBoldAppFont(28f));
        title.setForeground(AppColors.STRICT_WHITE_COLOR);

        JLabel subTitle = new JLabel("Ayez une vue panoramique de votre gestionnaire de pack");
        subTitle.setFont(GlobalUtilities.getBoldSmallAppFont());
        subTitle.setForeground(AppColors.WHITE_COLOR);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.add(title , BorderLayout.NORTH);
        titleWrapper.add(subTitle , BorderLayout.SOUTH);

        titlePanel.add(titleWrapper , BorderLayout.WEST);

        return titlePanel;
    }

    public void updateData(int packsCount , int typesCount , int materialsCount , double functionalPercent , double defectivePercent , double brokenPercent , int assignmentsCount , double activePercent , double terminatedPercent , int personsCount , int servicesCount , int breakDownsCount  , int interventionsCount){

        body.removeAll();

        body.add(createMaterialsPanel(packsCount , typesCount , materialsCount , functionalPercent , defectivePercent , brokenPercent));
        body.add(createAssignmentsPanel(assignmentsCount , activePercent , terminatedPercent , personsCount , servicesCount));
        body.add(createBreakDownsPanel(breakDownsCount , interventionsCount));

        body.revalidate();
        body.repaint();

    }

    private JPanel createMaterialsPanel(int packsCount , int typesCount , int materialsCount , double functionalPercent , double defectivePercent , double brokenPercent) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Informations sur les matériaux");
        titleLabel.setFont(GlobalUtilities.getBoldAppFont(18f));
        titleLabel.setForeground(AppColors.STRICT_WHITE_COLOR);

        infosPanel.add(wrapInPanel(titleLabel));
        infosPanel.add(Box.createVerticalStrut(12));
        infosPanel.add(Box.createVerticalGlue());
        infosPanel.add(createWrappedInfo("• Nombre de packs : " , packsCount + (packsCount > 1 ? " packs informatiques" : " pack informatique")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Nombre de types : ", typesCount + (typesCount > 1 ? " types de matériaux" : " type de matériau")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Nombre de matériaux : ", materialsCount + (materialsCount > 1 ? " matériaux" : " matériel")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Taux de matériaux fonctionnels : ", String.format("%.2f", functionalPercent) + "% des matériaux"));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Taux de matériaux défectueux : ", String.format("%.2f", defectivePercent) + "% des matériaux"));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Taux de matériaux en panne : ", String.format("%.2f", brokenPercent) + "% des matériaux"));
        infosPanel.add(Box.createVerticalGlue());

        card.add(infosPanel, BorderLayout.WEST);

        return card;
    }

    private JPanel createAssignmentsPanel(int assignmentCount , double activePercent , double terminatedPercent , int personsCount , int servicesCount) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Informations sur les affectations");
        titleLabel.setFont(GlobalUtilities.getBoldAppFont(18f));
        titleLabel.setForeground(AppColors.STRICT_WHITE_COLOR);

        infosPanel.add(wrapInPanel(titleLabel));
        infosPanel.add(Box.createVerticalStrut(12));
        infosPanel.add(createWrappedInfo("• Nombre d'affectations : ", assignmentCount + (assignmentCount > 1 ? " affectations" : " affectation")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Taux d'affectations en cours : ", String.format("%.2f", activePercent) + "% des affectations"));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Taux d'affectations terminées : ", String.format("%.2f", terminatedPercent) + "% des affectations"));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Nombre d'attributaires : ", personsCount + (personsCount > 1 ? " personnes" : " personne")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Nombre de services des attributaires : ", servicesCount + (servicesCount > 1 ? " services" : " service")));
        infosPanel.add(Box.createVerticalGlue());

        card.add(infosPanel, BorderLayout.WEST);

        return card;
    }

    private JPanel createBreakDownsPanel(int breakDownsCount , int interventionsCount) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infosPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Informations sur les pannes subvenues");
        titleLabel.setFont(GlobalUtilities.getBoldAppFont(18f));
        titleLabel.setForeground(AppColors.STRICT_WHITE_COLOR);

        infosPanel.add(wrapInPanel(titleLabel));
        infosPanel.add(Box.createVerticalStrut(12));
        infosPanel.add(Box.createVerticalGlue());
        infosPanel.add(createWrappedInfo("• Nombre total de pannes enregistrées : ", breakDownsCount + (breakDownsCount > 1 ? " pannes" : " panne")));
        infosPanel.add(Box.createVerticalStrut(7));
        infosPanel.add(createWrappedInfo("• Nombre total d'interventions enregistrées : ", interventionsCount + (interventionsCount > 1 ? " interventions" : " intervention")));
        infosPanel.add(Box.createVerticalGlue());

        card.add(infosPanel, BorderLayout.WEST);

        return card;
    }

    private JPanel createWrappedInfo(String text1 , String text2){
        JPanel panel = new JPanel(flowLayout);
        panel.setOpaque(false);

        JLabel label1 = new JLabel(text1);
        label1.setFont(GlobalUtilities.getBoldAppFont(16f));
        label1.setForeground(AppColors.MEDIUM_BASE_BLUE_COLOR);

        JLabel label2 = new JLabel(text2);
        label2.setFont(GlobalUtilities.getBoldAppFont(14f));
        label2.setForeground(AppColors.GRAY_COLOR);

        panel.add(label1);
        panel.add(label2);

        return panel;
    }

    private JPanel wrapInPanel(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.DARK_BLUE_COLOR , 1 , 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(AppColors.DARK_BLUE_COLOR);
        panel.add(label, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return panel;
    }

}