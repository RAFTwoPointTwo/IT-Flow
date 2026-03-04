package views;

import utilities.AppColors;
import utilities.GlobalUtilities;
import utilities.PageNames;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private final SideBar sideBar;
    private final CardLayout contentLayout;
    private final JPanel content;

    private final DashboardPage dashboardPage = new DashboardPage();
    private final MaterialsPage materialsPage = new MaterialsPage();
    private final AssignmentsPage assignmentsPage = new AssignmentsPage();
    private final BreakDownsPage breakDownsPage = new BreakDownsPage();
    private final MaterialsSettingsPage materialsSettingsPage = new MaterialsSettingsPage();
    private final InterventionsPage interventionsPage = new InterventionsPage();

    public HomePage() {

        setPreferredSize(GlobalUtilities.getCommonPageDimension());
        setLayout(new BorderLayout());
        setBackground(AppColors.WHITE_COLOR);

        sideBar = new SideBar();

        contentLayout = new CardLayout();

        content = new JPanel(contentLayout);
        content.setBackground(AppColors.WHITE_COLOR);

        addPage(dashboardPage , PageNames.DASHBOARD_PAGE);
        addPage(materialsPage , PageNames.MATERIALS_PAGE);
        addPage(assignmentsPage , PageNames.ASSIGNMENTS_PAGE);
        addPage(breakDownsPage , PageNames.BREAKDOWNS_PAGE);
        addPage(materialsSettingsPage , PageNames.MATERIALS_SETTINGS_PAGE);
        addPage(interventionsPage , PageNames.INTERVENTIONS_PAGE );

        add(sideBar , BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

    }

    public SideBar getSideBar() { return sideBar; }

    public DashboardPage getDashboardPage() { return dashboardPage; }

    public MaterialsPage getMaterialsPage() { return materialsPage; }

    public AssignmentsPage getAssignmentsPage() { return assignmentsPage; }

    public BreakDownsPage getBreakDownsPage() { return breakDownsPage; }

    public InterventionsPage getInterventionsPage() { return interventionsPage; }

    public MaterialsSettingsPage getMaterialsSettingsPage() { return materialsSettingsPage; }

    private void addPage(JPanel page , String pageName){ content.add(page , pageName); }

    public void showPage(String pageName){ contentLayout.show(content , pageName); }

}
