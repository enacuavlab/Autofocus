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
		btnAccéléro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des accéléromètres");
			}
		});
		
		//Bouton magnétomètres 
		btnMagnéto = new Button(zone_btn,SWT.NONE);
		btnMagnéto.setText("Magnétomètres");
		btnMagnéto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des magnétomètres");		}
		});
		
		//Bouton gyromètres
		btnGyro = new Button(zone_btn,SWT.NONE);
		btnGyro.setText("Gyromètres");
		btnGyro.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				titre.setText("Calibration des gyromètres");
			}
		});
				
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

