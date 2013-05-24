/**Application qui va réaliser l'interface graphique
 * @author Guillaume SAAS
 */
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
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;



public class ApplicationAmélioré {
	private Display display;
	private Shell fenetre;
	private Label titre;
	private Button btnAccéléro,btnMagnéto,btnGyro;
	private Composite zone_btn;
	
	
	/**Constructeur qui va initialiser la forme globale de la fenètre 
	 * 
	 */
	public ApplicationAmélioré() {
		display = new Display();
		//La fenetre 
		fenetre = new Shell(display, SWT.SHELL_TRIM);
		fenetre.setText("Interface de Calibration");
		fenetre.setLayout(new BorderLayout(0, 0));
		
		//Pour définir la zone qui contient le titre 
		Composite zone_titre = new Composite(fenetre, SWT.NONE);
		zone_titre.setLayoutData(BorderLayout.NORTH);
		zone_titre.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//Modification du style du titre 
		titre = new Label(zone_titre, SWT.BORDER);
		titre.setFont(SWTResourceManager.getFont("Cantarell", 16, SWT.BOLD));
		titre.setAlignment(SWT.CENTER);
		
		
		//Ensemble qui va contenir les différentes options de calibration pour les choisir 
		Group options = new Group(fenetre, SWT.NONE);
		options.setText("Type de Calibration");
		options.setLayoutData(BorderLayout.WEST);
		options.setLayout(new FormLayout());
		
		//Pour placer les boutons/labels des types de calibration
		zone_btn = new Composite(options, SWT.NONE);
		FormData fd_zone_btn = new FormData();
		fd_zone_btn.bottom = new FormAttachment(0, 212);
		fd_zone_btn.right = new FormAttachment(0, 135);
		fd_zone_btn.top = new FormAttachment(0, 10);
		fd_zone_btn.left = new FormAttachment(0, -2);
		zone_btn.setLayoutData(fd_zone_btn);
		FillLayout fl_zone_btn = new FillLayout(SWT.VERTICAL);
		fl_zone_btn.spacing = 20;
		zone_btn.setLayout(fl_zone_btn);
		
		initialise();
	}
	/**Fonction qui réinitialise au début lors d'un quitter de l'utilisateur ou pour le lancement de l'application
	 * 
	 */
	private void initialise(){
		titre.setText("Veuillez choisir le type de la calibration");
		
		//Bouton accéléromètres
		btnAccéléro = new Button(zone_btn, SWT.NONE);
		btnAccéléro.setText("Accéléromètres");
		btnAccéléro.setEnabled(false);
		btnAccéléro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des accéléromètres");
				btnMagnéto.setEnabled(false);
				btnGyro.setEnabled(false);
			}
		});
		
		//Bouton magnétomètres 
		btnMagnéto = new Button(zone_btn,SWT.NONE);
		btnMagnéto.setText("Magnétomètres");
		btnMagnéto.setEnabled(false);
		btnMagnéto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des magnétomètres");
				btnAccéléro.setEnabled(false);
				btnGyro.setEnabled(false);
			}
		});
		
		//Bouton gyromètres
		btnGyro = new Button(zone_btn,SWT.NONE);
		btnGyro.setText("Gyromètres");
		btnGyro.setEnabled(false);
		btnGyro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des gyromètres");
				btnAccéléro.setEnabled(false);
				btnMagnéto.setEnabled(false);
			}
		});
		Composite zone_id = new Composite(fenetre, SWT.NONE);
		zone_id.setLayoutData(BorderLayout.CENTER);
		zone_id.setLayout(new FormLayout());
		
		
		//Pour avoir l'id du drone 
		Combo combo_id = new Combo(zone_id, SWT.BORDER);
		FormData fd_text_id = new FormData();
		fd_text_id.top = new FormAttachment(0, 27);
		fd_text_id.right = new FormAttachment(62, -90);
		combo_id.setLayoutData(fd_text_id);
		combo_id.add("Test");
		
		//Image
		CLabel label = new CLabel(zone_id, SWT.NONE);
		label.setImage(new Image(display,"Image/croix_rouge.gif"));
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(combo_id, 0, SWT.TOP);
		fd_label.left = new FormAttachment(combo_id, 15);
		label.setLayoutData(fd_label);
		label.setText("");
		//label.getImage().dispose();
		combo_id.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				System.out.println(""+ (((Combo)e.widget).getText()));
				//label.setImage(new Image(display,"check.jpeg"));	
			}
		});
		
		Label indic_id = new Label(zone_id, SWT.NONE);
		indic_id.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		FormData fd_indic_id = new FormData();
		fd_indic_id.top = new FormAttachment(combo_id, 0, SWT.TOP);
		fd_indic_id.right = new FormAttachment(combo_id, -6);
		indic_id.setLayoutData(fd_indic_id);
		indic_id.setText("Entrer le numéro\nde votre drone\n");
				
	}
	
	private void change_mod(){
	
	}
	
	public void execute(){
		fenetre.open(); 
		while (!fenetre.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
}

