package ihm;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.wb.swt.SWTResourceManager;
import swing2swt.layout.FlowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;


public class Application {

	public static void main(String[] args) {
	Display display = new Display();
	
	
	
	Shell Fenetre = new Shell(display, SWT.SHELL_TRIM);
	Fenetre.setText("Interface de Calibration");
	Fenetre.setLayout(new BorderLayout(0, 0));
	
	Composite zone_titre = new Composite(Fenetre, SWT.NONE);
	zone_titre.setLayoutData(BorderLayout.NORTH);
	zone_titre.setLayout(new FillLayout(SWT.HORIZONTAL));
	
	final Label Titre = new Label(zone_titre, SWT.BORDER);
	Titre.setFont(SWTResourceManager.getFont("Cantarell", 16, SWT.BOLD));
	Titre.setAlignment(SWT.CENTER);
	Titre.setText("Veuillez choisir le type de la calibration");
	
	Group Options = new Group(Fenetre, SWT.NONE);
	Options.setText("Type de Calibration");
	Options.setLayoutData(BorderLayout.WEST);
	Options.setLayout(new FormLayout());
	
	Composite Zone_Btn = new Composite(Options, SWT.NONE);
	FormData fd_Zone_Btn = new FormData();
	fd_Zone_Btn.bottom = new FormAttachment(0, 212);
	fd_Zone_Btn.right = new FormAttachment(0, 135);
	fd_Zone_Btn.top = new FormAttachment(0, 10);
	fd_Zone_Btn.left = new FormAttachment(0, -2);
	Zone_Btn.setLayoutData(fd_Zone_Btn);
	FillLayout fl_Zone_Btn = new FillLayout(SWT.VERTICAL);
	fl_Zone_Btn.spacing = 20;
	Zone_Btn.setLayout(fl_Zone_Btn);
	
	Button BtnAccéléro = new Button(Zone_Btn, SWT.NONE);
	BtnAccéléro.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des accéléromètres");
		}
	});
	BtnAccéléro.setText("Accéléromètres");
	
	Button BtnMagnéto = new Button(Zone_Btn, SWT.NONE);
	BtnMagnéto.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des magnétomètres");		}
	});
	BtnMagnéto.setText("Magnétomètres");
	
	Button BtnGyro = new Button(Zone_Btn, SWT.NONE);
	BtnGyro.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des gyromètres");
		}
	});
	BtnGyro.setText("Gyromètres");
	
	Composite composite_1 = new Composite(Fenetre, SWT.NONE);
	composite_1.setLayoutData(BorderLayout.CENTER);
	composite_1.setLayout(new BorderLayout(0, 0));
	
	Label lblExplication = new Label(composite_1, SWT.CENTER);
	lblExplication.setFont(SWTResourceManager.getFont("Cantarell", 12, SWT.NORMAL));
	lblExplication.setAlignment(SWT.CENTER);
	lblExplication.setLayoutData(BorderLayout.NORTH);
	lblExplication.setText("Explication générale\n");
	
	Composite composite_2 = new Composite(composite_1, SWT.NONE);
	composite_2.setLayoutData(BorderLayout.EAST);
	composite_2.setLayout(new FillLayout(SWT.VERTICAL));
	
	Label lblExplicationDtaille = new Label(composite_2, SWT.NONE);
	lblExplicationDtaille.setText("Titre explication");
	
	Label lblExplication_1 = new Label(composite_2, SWT.NONE);
	lblExplication_1.setText("Explication");
	
	Composite composite = new Composite(composite_1, SWT.NONE);
	composite.setLayoutData(BorderLayout.SOUTH);
	GridLayout gl_composite = new GridLayout(3, true);
	gl_composite.verticalSpacing = 0;
	composite.setLayout(gl_composite);
	
	Button btnNewButton = new Button(composite, SWT.NONE);
	btnNewButton.setText("New Button");
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	new Label(composite, SWT.NONE);
	
	Fenetre.open(); 
	
	while (!Fenetre.isDisposed()) {
		if (!display.readAndDispatch()){
			display.sleep();
		}
	}
	 
	display.dispose();
	}
}


