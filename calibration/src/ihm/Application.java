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
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;


public class Application {
	private static Text text;
	private static Text text_1;

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
	
	final Button BtnAccelero = new Button(Zone_Btn, SWT.NONE);
	BtnAccelero.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des accéléromètres");
		}
	});
	BtnAccelero.setText("Accéléromètres");
	
	final Button BtnMagneto = new Button(Zone_Btn, SWT.NONE);
	BtnMagneto.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des magnétomètres");		}
	});
	BtnMagneto.setText("Magnétomètres");
	
	Button BtnGyro = new Button(Zone_Btn, SWT.NONE);
	BtnGyro.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Titre.setText("Calibration des gyromètres");
		}
	});
	BtnGyro.setText("Gyromètres");
	
	Composite composite = new Composite(Fenetre, SWT.NONE);
	composite.setLayoutData(BorderLayout.CENTER);
	composite.setLayout(new FormLayout());
	
	text_1 = new Text(composite, SWT.BORDER);
	text_1.setText("youhou");
	text_1.addModifyListener(new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			System.out.println("nouvelle valeur = " + (text_1).getText());
		}
	});
	
	FormData fd_text_1 = new FormData();
	fd_text_1.top = new FormAttachment(0, 27);
	fd_text_1.right = new FormAttachment(55, -90);
	text_1.setLayoutData(fd_text_1);
	
	Label lblEntrerLeNumro = new Label(composite, SWT.NONE);
	lblEntrerLeNumro.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
	FormData fd_lblEntrerLeNumro = new FormData();
	fd_lblEntrerLeNumro.top = new FormAttachment(text_1, 0, SWT.TOP);
	fd_lblEntrerLeNumro.right = new FormAttachment(text_1, -6);
	lblEntrerLeNumro.setLayoutData(fd_lblEntrerLeNumro);
	lblEntrerLeNumro.setText("Entrer le numéro\nde votre drone\n");

	
	
	
	Fenetre.open(); 
	
	while (!Fenetre.isDisposed()) {
		if (!display.readAndDispatch()){
			display.sleep();
		}
	}
	 
	display.dispose();
	}
}


