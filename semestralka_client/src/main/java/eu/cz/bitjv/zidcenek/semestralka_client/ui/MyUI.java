package eu.cz.bitjv.zidcenek.semestralka_client.ui;

//import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import eu.cz.bitjv.zidcenek.semestralka_client.clients.AnimalClient;
import eu.cz.bitjv.zidcenek.semestralka_client.clients.FeedingClient;
import eu.cz.bitjv.zidcenek.semestralka_client.clients.KeeperClient;
import eu.cz.bitjv.zidcenek.semestralka_client.clients.SpecieClient;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Animal;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.AnimalBox;
import eu.cz.fit.bitjv .zidcenek.semestralka.entity.Feeding;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.FeedingBox;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Keeper;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.KeeperBox;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.Specie;
import eu.cz.fit.bitjv.zidcenek.semestralka.entity.SpecieBox;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import static javax.swing.text.html.HTML.Tag.H1;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    final VerticalLayout layout = new VerticalLayout();
        //Layouts
        HorizontalLayout animalLayout = new HorizontalLayout();
        HorizontalLayout specieLayout = new HorizontalLayout();
        HorizontalLayout keeperLayout = new HorizontalLayout();
        HorizontalLayout feedingLayout = new HorizontalLayout();
        
        //Clients
        AnimalClient animalClient = new AnimalClient();
        SpecieClient specieClient = new SpecieClient();
        KeeperClient keeperClient = new KeeperClient();
        FeedingClient feedingClient = new FeedingClient();
        
        //Grids
        Grid<Animal> animalGrid = new Grid<Animal>();
        Grid<Specie> specieGrid = new Grid<Specie>();
        Grid<Keeper> keeperGrid = new Grid<Keeper>();
        Grid<Feeding> feedingGrid = new Grid<Feeding>();
        
        //Top buttons
        Button animalSwitch = new Button("Animals");
        Button specieSwitch = new Button("Species");
        Button keeperSwitch = new Button("Keepers");
        Button feedingSwitch = new Button("Feedings");
        
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        animalLayout . setSizeFull();
        animalLayout . setVisible(false);
        specieLayout . setSizeFull();
        keeperLayout . setSizeFull();
        keeperLayout . setVisible(false);
        feedingLayout . setSizeFull();
        feedingLayout . setVisible(false);
        
        setGrids();
        
        //Heading
        Label heading = new Label("Specie");
        heading . setHeight ( "3em" );
        heading . setStyleName ("my_heading");
        heading . setWidth("100%");
        Styles styles = Page.getCurrent().getStyles();
        styles . add ( ".my_heading { font-size: xx-large; }" );
        //heading . styles()
        //Top Buttons
        HorizontalLayout switchers = new HorizontalLayout();

        Label switcherHeading = new Label("Switch tables:");
        switcherHeading . setStyleName ("switch_label");
        styles = Page.getCurrent().getStyles();
        styles . add(".switch_label{font-size: large; margin-tom: 1em;}");
        switchers . addComponents(switcherHeading, animalSwitch, specieSwitch, keeperSwitch, feedingSwitch);
        
        
        //Specie add
        final TextField specieID = new TextField("ID:");
        specieID.setEnabled(false);
        final TextField specieName = new TextField("Name:");
        final TextField specieSecondName = new TextField("Second name:");
        ListSelect<Keeper> specieKeepers = new ListSelect<>("Is taken care of by:");    
        specieKeepers . setWidth("180px");
        specieKeepers . setHeight("100px");
        specieKeepers . setItemCaptionGenerator(Keeper::getName);
        specieKeepers . setItems ( getKeepers() );
        Button deselectKeeperSpecies = new Button ("Deselect all keepers");
        Button addSpecieButton = new Button("Add a specie");
        Button editSpecieButton = new Button("Click to save changes");
        Button deleteSpecieButton = new Button("Delete");
        
        VerticalLayout addSpecieLayout = new VerticalLayout();
        addSpecieLayout . addComponents ( specieID, specieName, specieSecondName,
                specieKeepers, deselectKeeperSpecies, addSpecieButton, editSpecieButton, deleteSpecieButton );    
        addSpecieButton.addClickListener(e -> { // add a new specie
            Specie newSpecie = new Specie();
            if ( specieName . getValue() . equals("") || specieSecondName . getValue() . equals ("") ){
                fillAllFields();
            }else{
                newSpecie . setFirstName ( specieName . getValue() );
                newSpecie . setSecondName ( specieSecondName . getValue() );
                Set<Keeper> tmpKeepers = specieKeepers . getValue();
                List<Keeper> tmpListKeepers = new ArrayList<>();
                tmpListKeepers . addAll(tmpKeepers);

                newSpecie . setKeepers(tmpListKeepers);   

                specieClient . create(newSpecie);
                specieGrid . setItems(getSpecies());
                specieName . setValue("");
                specieSecondName . setValue("");
                specieKeepers . setValue(Collections.emptySet());
            }
        });
        
        deselectKeeperSpecies.addClickListener(e -> {
            specieKeepers . deselectAll();
        });
        
        //Specie grid selection
        specieGrid.addSelectionListener(event -> { // add specie button
            Set<Specie> selected = event.getAllSelectedItems();
            if (selected.size() > 0) {
                Specie selectedSpecie = selected.stream().findFirst().get();               
                addSpecieButton.setVisible(false);
                editSpecieButton.setVisible(true);
                deleteSpecieButton.setVisible(true);
                deselectKeeperSpecies.setVisible(true);
                
                specieID.setValue(selectedSpecie.getId().toString());
                specieName.setValue(selectedSpecie.getFirstName());
                specieSecondName.setValue(selectedSpecie.getSecondName());
                specieKeepers.setItems(getKeepers());
                Set<Keeper> tmp = new HashSet<Keeper>(selectedSpecie . getKeepers());
                specieKeepers.setValue(tmp);
            } else {
                specieID.setValue("");
                specieName.setValue("");
                specieSecondName.setValue("");
                //specieKeepers . setValue(null);
            }            
        });
        
        editSpecieButton . addClickListener (e ->{ //edit specie button
            if ( specieName . getValue() . equals("") || specieSecondName . getValue() . equals ("") ){
                fillAllFields();
            }else{
                Specie newSpecie = new Specie();
                newSpecie.setId(Long.parseLong(specieID.getValue()));
                newSpecie . setFirstName ( specieName . getValue() );
                newSpecie . setSecondName ( specieSecondName . getValue() );
                Set<Keeper> tmpKeepers = specieKeepers . getValue();
                List<Keeper> tmpListKeepers = new ArrayList<>();
                tmpListKeepers . addAll(tmpKeepers);
                newSpecie . setKeepers(tmpListKeepers);   
                specieClient . edit_JSON( newSpecie, specieID.getValue() );
                specieGrid . setItems(getSpecies());
                addSpecieButton.setVisible(true);
                editSpecieButton.setVisible(false);
                deleteSpecieButton.setVisible(false);
                deselectKeeperSpecies.setVisible(true);
            }
        });
        
        deleteSpecieButton . addClickListener ( e->{
            specieClient . remove(specieID . getValue());
            if ( specieClient . getSpecieById( parseInt( specieID . getValue()) ) == null ){
                specieID.setValue("");
                specieName.setValue("");
                specieSecondName.setValue(""); //non-cascade delete - probably good but have to tell users
                specieGrid . setItems ( getSpecies() );
                //specieKeepers . setValue(null);
                addSpecieButton.setVisible(true);
                editSpecieButton.setVisible(false);
                deleteSpecieButton.setVisible(false);
                deselectKeeperSpecies.setVisible(true);    
           }else {
                cannotBeDeleted();
            }
        });
        
        //Animal add
        final TextField animalID = new TextField("ID:");
        animalID.setEnabled(false);
        final TextField animalName = new TextField("Name:");
        final ComboBox<String> animalGender = new ComboBox("Gender:");
        animalGender . setItems ("Male", "Female", "Unknown");
        DateField animalBirthDate = new DateField("Date of birth:");
        ComboBox<Specie> animalCB = new ComboBox<>("Species");
        animalCB.setItemCaptionGenerator(Specie::getFirstName);
        Button addAnimalButton = new Button("Add a specie");
        Button editAnimalButton = new Button("Click to save changes");
        Button deleteAnimalButton = new Button("Delete");
        
        VerticalLayout addAnimalLayout = new VerticalLayout();
        addAnimalLayout . addComponents ( animalID, animalName, animalGender,
                animalBirthDate, animalCB, addAnimalButton, editAnimalButton, deleteAnimalButton );    
        
        addAnimalButton.addClickListener(e -> {
            Animal newAnimal = new Animal();
            //Specie tmpSpecie = specieClient . getSpecieById (animalCB . getValue() . getId());
            if ( animalName . getValue() . equals("") || animalGender . isEmpty() 
                    || animalCB . isEmpty() ){
                fillAllFields();
            }else{
                newAnimal . setName ( animalName . getValue() );
                newAnimal . setGender ( animalGender . getValue() );
                newAnimal . setSpecie ( animalCB . getValue() );
                newAnimal . setBirthDate ( animalBirthDate . getValue() );
                animalClient . create_JSON( newAnimal );
                animalGrid . setItems(getAnimals());
                animalID.setValue("");
                animalName.setValue("");
                animalGender.setValue("");
                animalBirthDate.setValue(null);
                animalCB.setValue(null);
            }
        });
        
        //Animal grid selection
        animalGrid.addSelectionListener(event -> { // add animal button
            Set<Animal> selected = event.getAllSelectedItems();
            if (selected.size() > 0) {
                Animal selectedAnimal = selected.stream().findFirst().get();               
                addAnimalButton.setVisible(false);
                editAnimalButton.setVisible(true);
                deleteAnimalButton.setVisible(true);
                
                animalID.setValue(selectedAnimal.getId().toString());
                animalName.setValue(selectedAnimal.getName());
                animalGender.setValue(selectedAnimal.getGender());
                animalBirthDate.setValue(selectedAnimal.getBirthDate());
                animalCB.setItems(getSpecies());
                animalCB.setValue( selectedAnimal . getSpecie() ); 
                editAnimalButton . setVisible(true);
            } else {
                animalID.setValue("");
                animalName.setValue("");
                animalGender.setValue("");
                animalBirthDate.setValue(null);
                animalCB.setValue(null);
            }            
        });
        
        editAnimalButton . addClickListener (e ->{ //edit animal button
            if ( animalName . getValue() . equals("") || animalGender . isEmpty() 
                    || animalCB . isEmpty() ){
                fillAllFields();
            }else{
                Animal newAnimal = new Animal();
                newAnimal.setId(Long.parseLong(animalID.getValue()));
                newAnimal . setName ( animalName . getValue() );
                newAnimal . setGender ( animalGender . getValue() );
                newAnimal . setSpecie ( animalCB . getValue() );
                newAnimal . setBirthDate ( animalBirthDate . getValue() );
                animalClient . edit_JSON( newAnimal, animalID.getValue() );
                animalGrid . setItems(getAnimals());
                addAnimalButton.setVisible(true);
                editAnimalButton.setVisible(false);
                deleteAnimalButton.setVisible(false);
            }
        });
        
        deleteAnimalButton . addClickListener(e ->{
            animalClient . remove(animalID . getValue());
            animalGrid . setItems ( getAnimals() );
            animalID.setValue("");
            animalName.setValue("");
            animalGender.setValue("");
            animalCB.setValue(null);
            animalBirthDate.setValue(null);
            addAnimalButton.setVisible(true);
            editAnimalButton.setVisible(false);
            deleteAnimalButton.setVisible(false);
        });
        
        
        //Keeper add
        final TextField keeperID = new TextField("ID:");
        keeperID . setEnabled ( false );
        final TextField keeperName = new TextField("Name:");
        final ComboBox<String> keeperGender = new ComboBox("Gender:");
        keeperGender . setItems("Male", "Female", "Other");
        ListSelect<Specie> keeperSpecies = new ListSelect<>("Is taken care of by:");        
        keeperSpecies . setItemCaptionGenerator(Specie::getFirstName);
        
        Button addKeeperButton = new Button ("Add a keeper");
        Button editKeeperButton = new Button("Click to save changes");
        Button deleteKeeperButton = new Button("Delete");
        VerticalLayout addKeeperLayout = new VerticalLayout();
        addKeeperLayout . addComponents( keeperID, keeperName, keeperGender,
                //keeperSpecies, 
                addKeeperButton, editKeeperButton, deleteKeeperButton );
        
        addKeeperButton . addClickListener (e -> { // add keeper button
            if ( keeperName . getValue() . equals("") || keeperGender . isEmpty() ){
                fillAllFields();
            }else{
                Keeper newKeeper = new Keeper();
                newKeeper . setName ( keeperName . getValue() );
                newKeeper . setGender ( keeperGender . getValue() );

                Set<Specie> tmpSpecies = keeperSpecies . getValue();
                List<Specie> tmpListSpecies = new ArrayList<>();
                tmpListSpecies . addAll(tmpSpecies);
                newKeeper . setSpecies( tmpListSpecies ); 

                keeperClient . create( newKeeper );
                keeperGrid . setItems(getKeepers());
                keeperID.setValue("");
                keeperName.setValue("");
                keeperGender.setValue("");
            }
        });
        
        //Keeper grid selection
        keeperGrid.addSelectionListener(event -> { // add keeper button
            Set<Keeper> selected = event.getAllSelectedItems();
            if (selected.size() > 0) {
                Keeper selectedKeeper = selected.stream().findFirst().get();               
                addKeeperButton.setVisible(false);
                editKeeperButton.setVisible(true);
                deleteKeeperButton.setVisible(true);
                
                keeperID.setValue(selectedKeeper.getId().toString());
                keeperName.setValue(selectedKeeper.getName());
                keeperGender.setValue(selectedKeeper.getGender());
            } else {
                keeperID.setValue("");
                keeperName.setValue("");
                keeperGender.setValue("");
            }            
        });
        
        editKeeperButton . addClickListener (e ->{ //edit keeper button
            if ( keeperName . getValue() . equals("") || keeperGender . isEmpty() ){
                fillAllFields();
            }else{
                Keeper newKeeper = new Keeper();
                newKeeper.setId(Long.parseLong(keeperID.getValue()));
                newKeeper . setName ( keeperName . getValue() );
                newKeeper . setGender ( keeperGender . getValue() );
                keeperClient . edit_JSON( newKeeper, keeperID.getValue() );
                keeperGrid . setItems(getKeepers());
                addKeeperButton.setVisible(true);
                editKeeperButton.setVisible(false);
                deleteKeeperButton.setVisible(false);
            }
        });
        
        deleteKeeperButton . addClickListener ( e->{
            keeperClient . remove(keeperID . getValue());
            if ( keeperClient . getKeeperById( parseInt( keeperID . getValue()) ) == null ){
                keeperID.setValue("");
                keeperName.setValue("");
                keeperGender.setValue(""); //non-cascade delete - probably good but have to tell users
                keeperGrid . setItems ( getKeepers() );
                addKeeperButton.setVisible(true);
                editKeeperButton.setVisible(false);
                deleteKeeperButton.setVisible(false);
           }else {
                cannotBeDeleted();
            }
        });
        
        //Feeding add
        final TextField feedingID = new TextField("ID:");
        feedingID . setEnabled(false);
        final TextField feedingFoodType = new TextField("Type of food:");
        final ComboBox<Specie> feedingSpecieCB = new ComboBox<>("Who has been fed:");
        final ComboBox<Keeper> feedingKeeperCB = new ComboBox<>("Food provider:");
        final TextField feedingAmount = new TextField("Amount of food (number):");
        final DateField feedingFeedingTime = new DateField("Time of feeding:");
        
        feedingSpecieCB.setItemCaptionGenerator(Specie::getFirstName);
        feedingKeeperCB.setItemCaptionGenerator(Keeper::getName);
        
        Button addFeedingButton = new Button("Add a feeding record");
        Button editFeedingButton = new Button("Click to save changes");
        Button deleteFeedingButton = new Button("Delete");
        
        VerticalLayout addFeedingLayout = new VerticalLayout();
        addFeedingLayout . addComponents ( feedingID, feedingFoodType, feedingSpecieCB, feedingKeeperCB, feedingAmount,
                feedingFeedingTime, addFeedingButton, editFeedingButton, deleteFeedingButton );
        
        addFeedingButton . addClickListener ( e -> {
            if ( feedingFoodType . getValue() . equals("") || feedingFeedingTime . isEmpty()
                    || feedingAmount . getValue() . equals ("")
                    || feedingKeeperCB . isEmpty()
                    || feedingSpecieCB . isEmpty()){
                fillAllFields();
            }else{
                Feeding newFeeding = new Feeding();
                newFeeding . setTypeOfFood( feedingFoodType . getValue() );
                newFeeding . setFeedingTime( feedingFeedingTime . getValue() );
                newFeeding . setAmount ( Integer . parseInt ( feedingAmount . getValue() ) );
                newFeeding . setFoodProvider ( feedingKeeperCB . getValue() );
                newFeeding . setSpecieFed ( feedingSpecieCB . getValue() );
                feedingClient . create( newFeeding );
                feedingGrid . setItems(getFeedings());
                feedingID.setValue("");
                feedingFoodType.setValue("");
                feedingFeedingTime.setValue(null);
                feedingAmount.setValue("");
            }
        });
        
        //Feeding grid selection
        feedingGrid.addSelectionListener(event -> { // add feeding button
            Set<Feeding> selected = event.getAllSelectedItems();
            if (selected.size() > 0) {
                Feeding selectedFeeding = selected.stream().findFirst().get();               
                addFeedingButton.setVisible(false);
                editFeedingButton.setVisible(true);
                deleteFeedingButton.setVisible(true);
                
                feedingID.setValue(selectedFeeding.getId().toString());
                feedingFoodType.setValue(selectedFeeding.getTypeOfFood());
                feedingFeedingTime.setValue(selectedFeeding.getFeedingTime());
                feedingAmount.setValue(selectedFeeding.getAmount() . toString() );
                feedingKeeperCB.setItems(getKeepers());
                feedingKeeperCB.setValue(selectedFeeding . getFoodProvider() );
                feedingSpecieCB.setItems(getSpecies());
                feedingSpecieCB.setValue(selectedFeeding . getSpecieFed() );
                
            } else {
                feedingID.setValue("");
                feedingFoodType.setValue("");
                feedingFeedingTime.setValue(null);
                feedingAmount.setValue("");
            }            
        });
        
        editFeedingButton . addClickListener (e ->{ //edit feeding button
            if ( feedingFoodType . getValue() . equals("") || feedingFeedingTime . getValue() . equals ("")
                    || feedingAmount . getValue() . equals ("")
                    || feedingKeeperCB . getValue() . equals ("")
                    || feedingSpecieCB . getValue() . equals ("")){
                fillAllFields();
            }else{
                Feeding newFeeding = new Feeding();
                newFeeding.setId(Long.parseLong(feedingID.getValue()));
                newFeeding . setTypeOfFood( feedingFoodType . getValue() );
                newFeeding . setFeedingTime( feedingFeedingTime . getValue() );
                newFeeding . setAmount ( Integer . parseInt ( feedingAmount . getValue() ) );
                newFeeding . setFoodProvider ( feedingKeeperCB . getValue() );
                newFeeding . setSpecieFed ( feedingSpecieCB . getValue() );
                feedingClient . edit_JSON( newFeeding, feedingID.getValue() );
                feedingGrid . setItems(getFeedings());
                addFeedingButton.setVisible(true);
                editFeedingButton.setVisible(false);
                deleteFeedingButton.setVisible(false);
            }
        });
        
        deleteFeedingButton . addClickListener ( e->{
            feedingClient . remove(feedingID . getValue());
            if ( feedingClient . getFeedingById( parseInt( feedingID . getValue()) ) == null ){
                feedingID.setValue("");
                feedingFoodType.setValue("");
                feedingFeedingTime.setValue(null); //non-cascade delete - probably good but have to tell users
                feedingAmount.setValue(""); 
                feedingKeeperCB.setValue(null); 
                feedingSpecieCB.setValue(null); 
                feedingGrid . setItems ( getFeedings() );
                addFeedingButton.setVisible(true);
                editFeedingButton.setVisible(false);
                deleteFeedingButton.setVisible(false);
           }else {
                cannotBeDeleted();
            }
        });
        
        //Button visibility
        addAnimalButton . setVisible(true);
        editAnimalButton.setVisible(false);
        deleteAnimalButton.setVisible(false);
        addSpecieButton . setVisible(true);
        editSpecieButton.setVisible(false);
        deleteSpecieButton.setVisible(false);
        deselectKeeperSpecies.setVisible(true);
        addKeeperButton . setVisible(true);
        editKeeperButton.setVisible(false);
        deleteKeeperButton.setVisible(false);
        addFeedingButton . setVisible(true);
        editFeedingButton.setVisible(false);
        deleteFeedingButton.setVisible(false);
        
        HorizontalLayout searchLayout = new HorizontalLayout();
        TextField searchText = new TextField();
        Button searchButton = new Button("Search");
        searchButton.addClickListener(e -> {
            if (animalLayout.isVisible()) {
                AnimalBox tmp = animalClient.findSearchedAnimals_JSON(AnimalBox.class, searchText.getValue());
                animalGrid.setItems(tmp.getAnimals());
            } else if (specieLayout.isVisible()) {
                SpecieBox tmp = specieClient.findSearchedSpecies_JSON(SpecieBox.class, searchText.getValue());
                specieGrid.setItems(tmp.getSpecies());
            } else if (keeperLayout.isVisible()) {
                KeeperBox tmp = keeperClient.findSearchedKeepers_JSON(KeeperBox.class, searchText.getValue());
                keeperGrid.setItems(tmp.getKeepers());
            } else if ( feedingLayout.isVisible()  ) {
                FeedingBox tmp = feedingClient.findSearchedFeedings_JSON(FeedingBox.class, searchText.getValue());
                feedingGrid.setItems(tmp.getFeedings());
            }
        });
        searchLayout.addComponent(searchText);
        searchLayout.addComponent(searchButton);
        
        
        //Switchers
        animalSwitch . addClickListener(e -> {
            animalLayout . setVisible (true); 
            keeperLayout . setVisible (false); 
            specieLayout . setVisible (false); 
            feedingLayout . setVisible (false);
            animalGrid.setItems(getAnimals());
            animalCB . setItems ( getSpecies() );
            heading . setValue ("Animals");
            addAnimalButton . setVisible(true);
            editAnimalButton.setVisible(false);
            deleteAnimalButton.setVisible(false);
            deselectKeeperSpecies.setVisible(true);
        });
        
        specieSwitch . addClickListener(e -> {
             animalLayout . setVisible (false); 
            keeperLayout . setVisible (false); 
            specieLayout . setVisible (true); 
            feedingLayout . setVisible (false);
            specieGrid.setItems(getSpecies());
            addSpecieButton . setVisible(true);
            editSpecieButton.setVisible(false);
            deleteSpecieButton.setVisible(false);
            deselectKeeperSpecies.setVisible(true);
            specieKeepers . setItems ( getKeepers() );
           
            heading . setValue ("Species");
        });
        
        keeperSwitch . addClickListener(e -> {
            animalLayout . setVisible (false); 
            keeperLayout . setVisible (true); 
            specieLayout . setVisible (false); 
            feedingLayout . setVisible (false);
            keeperGrid.setItems(getKeepers());
            addFeedingButton . setVisible(true);
            editFeedingButton.setVisible(false);
            deleteFeedingButton.setVisible(false);
            keeperSpecies . setItems (getSpecies() );
            addKeeperButton . setVisible(true);
            editKeeperButton.setVisible(false);
            deleteKeeperButton.setVisible(false);
            heading . setValue ("Keepers");
        });
        
        feedingSwitch . addClickListener(e -> {
           animalLayout . setVisible (false); 
           keeperLayout . setVisible (false); 
           specieLayout . setVisible (false); 
           feedingLayout . setVisible (true);
           feedingGrid.setItems(getFeedings());
           feedingKeeperCB . setItems(getKeepers());
           feedingSpecieCB . setItems(getSpecies());
           addFeedingButton . setVisible(true);
            editFeedingButton.setVisible(false);
            deleteFeedingButton.setVisible(false);
           heading . setValue ("Feedings");
        });
        
        styles = Page.getCurrent().getStyles();
        //addSpecieLayout . setStyleName("my_form_style");
        //styles . add(".addSpecieLayout{margin-top: 1000px;}");
        animalLayout . addComponents ( animalGrid, addAnimalLayout );
        animalLayout . setExpandRatio ( animalGrid, 0.7f );
        animalLayout . setExpandRatio ( addAnimalLayout, 0.3f );
        specieLayout . addComponents ( specieGrid, addSpecieLayout );
        specieLayout . setExpandRatio ( specieGrid, 0.7f );
        specieLayout . setExpandRatio ( addSpecieLayout, 0.3f );
        keeperLayout . addComponents ( keeperGrid, addKeeperLayout );
        keeperLayout . setExpandRatio ( keeperGrid, 0.7f );
        keeperLayout . setExpandRatio ( addKeeperLayout, 0.3f );
        feedingLayout . addComponents ( feedingGrid, addFeedingLayout );
        feedingLayout . setExpandRatio ( feedingGrid, 0.7f );
        feedingLayout . setExpandRatio ( addFeedingLayout, 0.3f );
        layout.addComponents(heading, switchers, searchLayout, animalLayout, specieLayout, 
                keeperLayout, feedingLayout);
        setContent(layout);
    }
    private void setGrids(){
        //Animal Grid
        animalGrid.setItems(getAnimals());
        animalGrid.addColumn(Animal::getId).setCaption("ID");
        animalGrid.addColumn(Animal::getName).setCaption("Name");
        animalGrid.addColumn(Animal::getGender).setCaption("Gender");
        animalGrid.addColumn(Animal::getBirthDate).setCaption("BirthDate");
        animalGrid.addColumn(Animal::getSpecie).setCaption("Specie");
        animalGrid.setSizeFull();
        animalGrid . setStyleName ("my_grid_style");
        
        //Specie Grid
        specieGrid.setItems(getSpecies());
        specieGrid.addColumn(Specie::getId).setCaption("ID");
        specieGrid.addColumn(Specie::getFirstName).setCaption("First name");
        specieGrid.addColumn(Specie::getSecondName).setCaption("Second name");
        specieGrid.addColumn(Specie::keepersToString).setCaption("Keepers");
        specieGrid.setSizeFull();
        specieGrid . setStyleName ("my_grid_style");
        
        //Keeper Grid
        keeperGrid.setItems(getKeepers());
        keeperGrid.addColumn(Keeper::getId).setCaption("ID");
        keeperGrid.addColumn(Keeper::getName).setCaption("Name");
        keeperGrid.addColumn(Keeper::getGender).setCaption("Gender");
        keeperGrid.setSizeFull();
        keeperGrid . setStyleName ("my_grid_style");
        
        //Feeding Grid
        feedingGrid.setItems(getFeedings());
        feedingGrid.addColumn(Feeding::getId).setCaption("ID");
        feedingGrid.addColumn(Feeding::getTypeOfFood).setCaption("Food type");
        feedingGrid.addColumn(Feeding::getSpecieFed).setCaption("Specie");
        feedingGrid.addColumn(Feeding::getFoodProvider).setCaption("Keeper");
        feedingGrid.addColumn(Feeding::getAmount).setCaption("Amount");
        feedingGrid.addColumn(Feeding::getFeedingTime).setCaption("Feeding Time");
        feedingGrid.setSizeFull();
        feedingGrid . setStyleName ("my_grid_style");
        
        //Grid Styles
        Styles styles = Page.getCurrent().getStyles();
        styles . add(".my_grid_style{margin-top: 50px;}");
    }
    
    private List<Animal> getAnimals(){
        AnimalBox box = animalClient . findAll_JSON(AnimalBox.class);
        return box . getAnimals();
    }
    private List<Specie> getSpecies(){
        SpecieBox box = specieClient . findAll_JSON(SpecieBox.class);
        return box . getSpecies();
    }
    private List<Keeper> getKeepers(){
        KeeperBox box = keeperClient . findAll_JSON(KeeperBox.class);
        return box . getKeepers();
    }
    private List<Feeding> getFeedings(){
        FeedingBox box = feedingClient . findAll_JSON(FeedingBox.class);
        return box . getFeedings();
    }

    private void fillAllFields (){
        Notification.show("Please fill all required fields.", Notification.Type.HUMANIZED_MESSAGE);
    }
    
    private void cannotBeDeleted (){
        Notification.show("Cannot be deleted, first delete all dependencies.", Notification.Type.HUMANIZED_MESSAGE);
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
