/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.VotingChain.blockchain;
import static bean.VotingChain.difficulty;
import com.google.gson.GsonBuilder;
import db.Admins;
import db.Blockchain;
import db.Candidate;
import db.Comments;
import db.Department;
import db.Groups;
import db.Position;
import db.Quotes;
import db.Result;
import db.Results;
import db.Status;
import db.Voter;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;

/**
 *
 * @author KIMANI
 */
@ManagedBean
@SessionScoped
public class vote {

    @PersistenceContext(name = "muvotePU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private String password;
    private String username;
    private List<Admins> AdminsList = new ArrayList();
    private List<Admins> AdminsBlankList = new ArrayList();
    private Admins Admins = new Admins();

    private List<Candidate> CandidateList = new ArrayList();
    private List<Candidate> CandidateBlankList = new ArrayList();
    private Candidate Candidate = new Candidate();

    private List<Groups> GroupsList = new ArrayList();
    private List<Groups> GroupsBlankList = new ArrayList();
    private Groups Groups = new Groups();

    private List<Result> ResultList = new ArrayList();
    private List<Result> ResultBlankList = new ArrayList();
    private Result Result = new Result();

    private List<Results> ResultsList = new ArrayList();
    private List<Results> ResultsBlankList = new ArrayList();
    private Results Results = new Results();

    private List<Position> PositionList = new ArrayList();
    private List<Position> PositionBlankList = new ArrayList();
    private Position Position = new Position();

    private List<Voter> VoterList = new ArrayList();
    private List<Voter> VoterBlankList = new ArrayList();
    private Voter Voter = new Voter();

    public List<Blockchain> BlockchainList = new ArrayList();
    private List<Blockchain> BlockchainBlankList = new ArrayList();
    private Blockchain Blockchain = new Blockchain();

    private List<Status> StatusList = new ArrayList();
    private List<Status> StatusBlankList = new ArrayList();
    private Status Status = new Status();

    private List<Quotes> QuotesList = new ArrayList();
    private List<Quotes> QuotesBlankList = new ArrayList();
    private Quotes Quotes = new Quotes();

    private List<Department> DepartmentList = new ArrayList();
    private List<Department> DepartmentBlankList = new ArrayList();
    private Department Department = new Department();

    private List<Comments> CommentsList = new ArrayList();
    private List<Comments> CommentsBlankList = new ArrayList();
    private Comments Comments = new Comments();

    private MeterGaugeChartModel registeredCandidates;
    private MeterGaugeChartModel loggedSystemUsers;
    private MeterGaugeChartModel registeredPositions;
    private MeterGaugeChartModel votedVoters;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private boolean systemUserTab;
    private boolean candidateTab;
    private boolean positionTab;
    private boolean userGroupTab;
    private boolean voterTab;
    private boolean statusTab;
    private boolean DepartmentTab;
    private boolean commentsTab;
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private CartesianChartModel model2 = new CartesianChartModel();

    /**
     * Creates a new instance of vote
     */
    public vote() {
    }

    public String login() {
        try {
            Admins = (Admins) getEm().createQuery("SELECT s FROM Admins s WHERE s.username = '" + getUsername() + "' and s.password ='" + getPassword() + "'").getSingleResult();

            return "resultsPage.xhtml?faces-redirect=true";

        } catch (Exception ex) {
            ex.printStackTrace();

            try {
                Voter = (Voter) em.createQuery("SELECT s FROM Voter s WHERE s.password = '" + password + "' and s.emailadd = '" + username + "'").getSingleResult();
                System.out.println("the voter is " + Voter);
                return "voterPage.xhtml?faces-redirect=true";
            } catch (Exception ex2) {
                FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong credentials ", "Your details are not in the system please contact your administrator");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("votingForm", success);

            }

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong credentials ", "Your details are not in the system please contact your administrator");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("votingForm", success);
        }
        return "homePage.xhtml";
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) - 1);
        t.set(10, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) - 1);
        t.set(10, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(10, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(5, t.get(5) + 2);
        t.set(9, 1);
        t.set(10, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(10, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 0);
        t.set(5, t.get(5) + 1);
        t.set(10, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 0);
        t.set(5, t.get(5) + 1);
        t.set(10, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(9, 1);
        t.set(5, t.get(5) + 4);
        t.set(10, 3);

        return t.getTime();
    }

    public ScheduleEvent getEvent() {
        return this.event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (this.event.getId() == null) {
            this.eventModel.addEvent(this.event);
        } else {
            this.eventModel.updateEvent(this.event);
        }
        this.event = new DefaultScheduleEvent();
    }

    private static final File LOCATION = new File("C:/uploads");
    private UploadedFile file;

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            try {

                String prefix = FilenameUtils.getBaseName(Candidate.getIdcandidate().toString() + Candidate.getAlias());
                String suffix = FilenameUtils.getExtension(event.getFile().getFileName());
                File save = File.createTempFile(prefix + "-", "." + suffix, LOCATION);
                Files.write(save.toPath(), event.getFile().getContents(), new OpenOption[0]);

                Path path = save.toPath();

                em.find(Candidate.class, Candidate.getIdcandidate());
                String fullpath = path.toString().replace("\\", "/");
                System.out.println("the path is +++++++++++++++=" + fullpath);
                Candidate.setPhoto(fullpath);
                utx.begin();
                em.merge(Candidate);
                utx.commit();

                FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(vote.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onEventSelect(SelectEvent selectEvent) {
        this.event = ((ScheduleEvent) selectEvent.getObject());
    }

    public void onDateSelect(SelectEvent selectEvent) {
        this.event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
        addMessage(message);
    }

    public CartesianChartModel getModel2() {
        ResultsList = this.em.createQuery("SELECT r FROM Results r").getResultList();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        for (Results fp : this.ResultsList) {

            Candidate = (Candidate) em.createQuery("SELECT c FROM Candidate c WHERE c.idcandidate = '" + fp.getCandidateid() + "'").getSingleResult();
            series1.set(Candidate.getVoterid().getFirstname(), fp.getVotes());
        }

        this.model2.addSeries(series1);

        return this.model2;
    }

    public void setModel2(CartesianChartModel model2) {
        this.model2 = model2;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @PostConstruct
    public void init() {
        try {

            createMeterGaugeModels();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createMeterGaugeModels() {
        setRegisteredCandidates(registeredCandidates());
        setLoggedSystemUsers(systemUsers());
        setRegisteredPositions(registeredPositions());
        setVotedVoters(votedVoter());
    }

    private MeterGaugeChartModel registeredCandidates() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }
        return new MeterGaugeChartModel(140, intervals);
    }

    private MeterGaugeChartModel systemUsers() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }
        return new MeterGaugeChartModel(10, intervals);
    }

    private MeterGaugeChartModel registeredPositions() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }
        return new MeterGaugeChartModel(140, intervals);
    }

    private MeterGaugeChartModel votedVoter() {
        List<Number> intervals = new ArrayList() {
        };
        for (int i = 0; i < 200; i++) {
            intervals.add(i + 20);
        }
        return new MeterGaugeChartModel(140, intervals);
    }

    public String activateSystemUserTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = true;
        candidateTab = false;
        positionTab = false;
        userGroupTab = false;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    public String activateStatusTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = false;
        positionTab = false;
        userGroupTab = false;
        voterTab = false;
        statusTab = true;
        return "settings.xhtml";
    }

    public String activateCommentsTab() {
        setCommentsTab(true);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = false;
        positionTab = false;
        userGroupTab = false;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    public String activateCandidatesTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = true;
        positionTab = false;
        userGroupTab = false;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    public String activateDepartmentTab() {
        setCommentsTab(false);
        DepartmentTab = true;
        systemUserTab = false;
        candidateTab = false;
        positionTab = false;
        userGroupTab = false;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    public String activateVotersTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = false;
        positionTab = false;
        userGroupTab = false;
        voterTab = true;
        statusTab = false;
        return "settings.xhtml";
    }

    public String acivatePositionsTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = false;
        positionTab = true;
        userGroupTab = false;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    public String activateUserGroupsTab() {
        setCommentsTab(false);
        DepartmentTab = false;
        systemUserTab = false;
        candidateTab = false;
        positionTab = false;
        userGroupTab = true;
        voterTab = false;
        statusTab = false;
        return "settings.xhtml";
    }

    //status 
    public String createAdmins() {
        try {

            this.getUtx().begin();
            this.getEm().persist(this.getAdmins());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admins created", "Admins created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("AdminsForm", success);
            this.setAdmins(new Admins());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("AdminsForm", success);
        }
        return null;
    }

    public String editAdmins() {
        try {

            this.getEm().find(Admins.class, this.getAdmins().getAdminId());
            this.getUtx().begin();
            this.getEm().merge(this.getAdmins());
            this.getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admins updated", "Admins updated");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Admins", success);
            this.setAdmins(new Admins());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Admins", success);
        }
        return null;
    }

    public String deleteAdmins(Admins Admins) {
        try {

            this.getUtx().begin();
            Admins toBeRemoved = (Admins) this.getEm().merge(Admins);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Admins deleted", "Admins deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Admins", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Admins", success);
        }
        return null;
    }

    public String createCandidate() {
        try {

            getCandidate().setStaffid(Admins);
            getCandidate().setCreationdate(new java.util.Date());

            this.getUtx().begin();
            this.getEm().persist(this.getCandidate());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidate created", "Candidate created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("CandidateForm", success);
            Candidate.setIdcandidate(Candidate.getIdcandidate());

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("CandidateForm", success);
        }
        return null;
    }

    public String editCandidate() {
        try {

            getCandidate().setStaffid(Admins);
            getCandidate().setCreationdate(new java.util.Date());
            this.getEm().find(Candidate.class, this.getCandidate().getIdcandidate());
            this.getUtx().begin();
            this.getEm().merge(this.getCandidate());
            this.getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidate updated", "Candidate updated");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Candidate", success);
            this.setCandidate(new Candidate());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Candidate", success);
        }
        return null;
    }

    public String deleteCandidate(Candidate Candidate) {
        try {

            getCandidate().setStaffid(Admins);
            getCandidate().setCreationdate(new java.util.Date());
            this.getUtx().begin();
            Candidate toBeRemoved = (Candidate) this.getEm().merge(Candidate);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Candidate deleted", "Candidate deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Candidate", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Candidate", success);
        }
        return null;
    }

    public String createGroups() {
        try {

            this.getUtx().begin();
            this.getEm().persist(this.getGroups());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Groups created", "Groups created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);
            this.setGroups(new Groups());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);
        }
        return null;
    }

    public String editGroups() {
        try {
            this.getEm().find(Groups.class, this.getGroups().getIdgroups());
            this.getUtx().begin();
            this.getEm().merge(this.getGroups());
            this.getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Groups updated", "Groups updated");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);
            this.setGroups(new Groups());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);
        }
        return null;
    }

    public String deleteGroups(Groups Groups) {
        try {
            this.getUtx().begin();
            Groups toBeRemoved = (Groups) this.getEm().merge(Groups);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Groups deleted", "Groups deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Groups", success);
        }
        return null;
    }

    public String createPosition() {
        try {

            this.getUtx().begin();
            this.getEm().persist(this.getPosition());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Position created", "Position created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);
            this.setPosition(new Position());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);
        }
        return null;
    }

    public String editPosition() {
        try {
            this.getEm().find(Position.class, this.getPosition().getIdposition());
            this.getUtx().begin();
            this.getEm().merge(this.getPosition());
            this.getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Position updated", "Position updated");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);
            this.setPosition(new Position());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);
        }
        return null;
    }

    public String deletePosition(Position Position) {
        try {
            this.getUtx().begin();
            Position toBeRemoved = (Position) this.getEm().merge(Position);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Position deleted", "Position deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Position", success);
        }
        return null;
    }

    public String push() {
        try {
            ResultList = em.createQuery("select r from Result r").getResultList();
            for (int i = 0; i < 10; i++) {
                if (blockchain.size() - 1 < 0) {
                    blockchain.add(new Block(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname(), "0"));
                    System.out.println("Trying to Mine block " + ResultList.get(i).getIdresults() + "... ");
                    blockchain.get(0).mineBlock(difficulty);
                    utx.begin();
                    Blockchain.setCurrenthash(blockchain.get(0).hash);
                    Blockchain.setOriginaldata(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname());
                    Blockchain.setPrevioushash(blockchain.get(0).previousHash);
                    Blockchain.setTimeStamp(new java.util.Date());
                    em.persist(Blockchain);
                    utx.commit();
                } else {
                    blockchain.add(ResultList.get(i).getIdresults(), new Block(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname(), blockchain.get(ResultList.get(i).getIdresults() - 1).hash));
                    System.out.println("Trying to Mine block " + ResultList.get(i).getIdresults() + "... ");
                    blockchain.get(ResultList.get(i).getIdresults()).mineBlock(difficulty);
                    utx.begin();
                    Blockchain.setCurrenthash(blockchain.get(ResultList.get(i).getIdresults()).hash);
                    Blockchain.setOriginaldata(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname());
                    Blockchain.setPrevioushash(blockchain.get(ResultList.get(i).getIdresults()).previousHash);
                    Blockchain.setTimeStamp(new java.util.Date());
                    em.persist(Blockchain);
                    utx.commit();
                }
            }
            System.out.println("\nBlockchain is Valid: " + isChainValid());
            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
            System.out.println("\nThe block chain: ");
            System.out.println(blockchainJson);
            return " Generated a chain of " + blockchain.size() + " Records ";
        } catch (Exception ex) {

            Logger.getLogger(VotingChain.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public String createChain() {
        try {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chain created", push());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("profilefrm", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Chain failed", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("profilefrm", success);
            return null;
        }
    }

    public String createResult() {

        try {

            Result = (Result) em.createQuery("SELECT r from Result r where r.positionid.idposition = " + Candidate.getPositionid().getIdposition() + " and r.voterid.idvoter = " + Voter.getIdvoter() + "").getSingleResult();

            System.out.println(Result.getPositionid().getName());
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voted", "You have already voted for " + Result.getPositionid().getName());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Result", success);
            return null;

        } catch (Exception ex) {
            try {

                Result = (Result) em.createQuery("select r from Result r where r.positionid.idposition = " + Candidate.getPositionid().getIdposition() + " and r.candidateid.idcandidate = " + Candidate.getIdcandidate() + "").getSingleResult();
                System.out.println("the existing result is =======" + Result.getPositionid().getName());
                Result.setRegistrationyear(new java.util.Date().getYear());
                Result.setCreationate(new java.util.Date());
                Result.setPositionid(Candidate.getPositionid());
                Result.setVoterid(Voter);
                Result.setStatus(Candidate.getStatus());
                Result.setResult(Result.getResult() + 1);
                Result.setCandidateid(Candidate);
                // String data = "Registration year : " + Result.getRegistrationyear() + " Result  : " + Result.getResult() + " Candidate Name: " + Result.getCandidateid().getVoterid().getFirstname() + Result.getCandidateid().getVoterid().getSecondname() + " Date : " + Result.getCreationate() + " Position : " + Result.getPositionid().getName() + " Voter ID: " + Result.getVoterid().getFirstname() + Result.getVoterid().getSecondname();

                this.getUtx().begin();
                this.getEm().merge(Result);
                this.getUtx().commit();
                FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vote Successful", "Thank you for voting for " + Candidate.getVoterid().getFirstname() + " for the position of " + Candidate.getPositionid().getName() + " check out the results page to get updated");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("Result", success);
                this.setResult(new Result());
                return null;
            } catch (Exception exw) {

                try {
                 
                    Result.setRegistrationyear(new java.util.Date().getYear());
                    Result.setCreationate(new java.util.Date());
                    Result.setPositionid(Candidate.getPositionid());
                    Result.setVoterid(Voter);
                    Result.setStatus(Candidate.getStatus());
                    Result.setResult(1);
                    Result.setCandidateid(Candidate);
                    // String data = "Registration year : " + Result.getRegistrationyear() + " Result  : " + Result.getResult() + " Candidate Name: " + Result.getCandidateid().getVoterid().getFirstname() + Result.getCandidateid().getVoterid().getSecondname() + " Date : " + Result.getCreationate() + " Position : " + Result.getPositionid().getName() + " Voter ID: " + Result.getVoterid().getFirstname() + Result.getVoterid().getSecondname();

                    this.getUtx().begin();
                    this.getEm().persist(Result);
                    this.getUtx().commit();
                    FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vote Successful", "Thank you for voting for " + Candidate.getVoterid().getFirstname() + " for the position of " + Candidate.getPositionid().getName() + " check out the results page to get updated");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("Result", success);
                    this.setResult(new Result());

                    exw.printStackTrace();
                    return null;
                } catch (Exception ex1) {
                    Logger.getLogger(vote.class.getName()).log(Level.SEVERE, null, ex1);
                    return null;
                }
            }
        }

    }

    public String
            editResult() {
        try {
            this.getEm().find(Result.class, this.getResult().getIdresults());

            this.getUtx()
                    .begin();

            this.getEm()
                    .merge(this.getResult());

            this.getUtx()
                    .commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Result updated", "Result updated");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(
                    "Result", success);

            this.setResult(
                    new Result());

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Result", success);
        }
        return null;
    }

    public String deleteResult(Result Result) {
        try {
            this.getUtx().begin();
            Result toBeRemoved = (Result) this.getEm().merge(Result);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Result deleted", "Result deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Result", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Result", success);
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "homePage.xhtml?faces-redirect=true";
    }

    public String createVoter() {
        try {
            Voter.setStaffid(Admins);
            this.getUtx().begin();
            this.getEm().persist(this.getVoter());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Voter created", "Voter created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Voter", success);
            this.setVoter(new Voter());

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Voter", success);
        }
        return null;
    }

    public String
            editVoter() {
        try {
            this.getEm().find(Voter.class, this.getVoter().getIdvoter());

            this.getUtx()
                    .begin();

            this.getEm()
                    .merge(this.getVoter());

            this.getUtx()
                    .commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Voter updated", "Voter updated");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(
                    "Voter", success);

            this.setVoter(
                    new Voter());

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Voter", success);
        }
        return null;
    }

    public String deleteVoter(Voter Voter) {
        try {
            this.getUtx().begin();
            Voter toBeRemoved = (Voter) this.getEm().merge(Voter);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voter deleted", "Voter deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Sacco", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Sacco", success);
        }
        return null;
    }

    public String createStatus() {
        try {

            Status.setStaffid(Admins);
            this.getUtx().begin();
            this.getEm().persist(this.getStatus());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status created", "Status created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        return null;
    }

    public String
            editStatus() {
        try {
            this.getEm().find(Status.class, this.getStatus().getIdstatus());

            this.getUtx()
                    .begin();

            this.getEm()
                    .merge(this.getStatus());

            this.getUtx()
                    .commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status updated", "Status updated");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(
                    "Status", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        return null;
    }

    public String deleteStatus(Status Status) {
        try {
            this.getUtx().begin();
            Status toBeRemoved = (Status) this.getEm().merge(Status);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        return null;
    }

    public String createDept() {
        try {

            this.getUtx().begin();
            this.getEm().persist(this.getDepartment());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Department created", "Department created");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Department", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Department", success);
        }
        return null;
    }

    public String
            editDept() {
        try {
            this.getEm().find(Department.class, this.getDepartment().getIddepartment());

            this.getUtx()
                    .begin();

            this.getEm()
                    .merge(this.getDepartment());

            this.getUtx()
                    .commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Department updated", "Department updated");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(
                    "Department", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Department", success);
        }
        return null;
    }

    public String deleteDept(Department Department) {
        try {
            this.getUtx().begin();
            Department toBeRemoved = (Department) this.getEm().merge(Department);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Department deleted", "Department deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Department", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Department", success);
        }
        return null;
    }

    public String createComments() {
        try {

            this.getUtx().begin();
            this.getEm().persist(this.getComments());
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Comment Received", "Thank you " + Comments.getName() + " We have received your comment. we will look into it and give a response");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Comments", success);
            Comments = new Comments();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Comments", success);
        }
        return null;
    }

    public String
            editComments() {
        try {
            this.getEm().find(Comments.class, this.getComments().getIdcomments());

            this.getUtx()
                    .begin();

            this.getEm()
                    .merge(this.getComments());

            this.getUtx()
                    .commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Comments updated", "Comments updated");
            FacesContext context = FacesContext.getCurrentInstance();

            context.addMessage(
                    "Comments", success);

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Comments", success);
        }
        return null;
    }

    public String deleteComments(Comments Comments) {
        try {
            this.getUtx().begin();
            Comments toBeRemoved = (Comments) this.getEm().merge(Comments);
            this.getEm().remove(toBeRemoved);
            this.getUtx().commit();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Comments deleted", "Comments deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Comments", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Comments", success);
        }
        return null;
    }

    /**
     * @return the AdminsList
     */
    public List<Admins> getAdminsList() {
        AdminsList = getEm().createQuery("SELECT a FROM Admins a").getResultList();
        return AdminsList;
    }

    /**
     * @param AdminsList the AdminsList to set
     */
    public void setAdminsList(List<Admins> AdminsList) {
        this.AdminsList = AdminsList;
    }

    /**
     * @return the AdminsBlankList
     */
    public List<Admins> getAdminsBlankList() {
        return AdminsBlankList;
    }

    /**
     * @param AdminsBlankList the AdminsBlankList to set
     */
    public void setAdminsBlankList(List<Admins> AdminsBlankList) {
        this.AdminsBlankList = AdminsBlankList;
    }

    /**
     * @return the Admins
     */
    public Admins getAdmins() {
        return Admins;
    }

    /**
     * @param Admins the Admins to set
     */
    public void setAdmins(Admins Admins) {
        this.Admins = Admins;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the utx
     */
    public UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the CandidateList
     */
    public List<Candidate> getCandidateList() {
        CandidateList = em.createQuery("SELECT c FROM Candidate c").getResultList();
        return CandidateList;
    }

    /**
     * @param CandidateList the CandidateList to set
     */
    public void setCandidateList(List<Candidate> CandidateList) {
        this.CandidateList = CandidateList;
    }

    /**
     * @return the CandidateBlankList
     */
    public List<Candidate> getCandidateBlankList() {
        return CandidateBlankList;
    }

    /**
     * @param CandidateBlankList the CandidateBlankList to set
     */
    public void setCandidateBlankList(List<Candidate> CandidateBlankList) {
        this.CandidateBlankList = CandidateBlankList;
    }

    /**
     * @return the Candidate
     */
    public Candidate getCandidate() {
        return Candidate;
    }

    /**
     * @param Candidate the Candidate to set
     */
    public void setCandidate(Candidate Candidate) {
        this.Candidate = Candidate;
    }

    /**
     * @return the GroupsList
     */
    public List<Groups> getGroupsList() {
        GroupsList = em.createQuery("SELECT g FROM Groups g").getResultList();
        return GroupsList;
    }

    /**
     * @param GroupsList the GroupsList to set
     */
    public void setGroupsList(List<Groups> GroupsList) {
        this.GroupsList = GroupsList;
    }

    /**
     * @return the GroupsBlankList
     */
    public List<Groups> getGroupsBlankList() {
        return GroupsBlankList;
    }

    /**
     * @param GroupsBlankList the GroupsBlankList to set
     */
    public void setGroupsBlankList(List<Groups> GroupsBlankList) {
        this.GroupsBlankList = GroupsBlankList;
    }

    /**
     * @return the Groups
     */
    public Groups getGroups() {
        return Groups;
    }

    /**
     * @param Groups the Groups to set
     */
    public void setGroups(Groups Groups) {
        this.Groups = Groups;
    }

    /**
     * @return the PositionList
     */
    public List<Position> getPositionList() {
        PositionList = em.createQuery("SELECT p FROM Position p").getResultList();
        return PositionList;
    }

    /**
     * @param PositionList the PositionList to set
     */
    public void setPositionList(List<Position> PositionList) {
        this.PositionList = PositionList;
    }

    /**
     * @return the PositionBlankList
     */
    public List<Position> getPositionBlankList() {
        return PositionBlankList;
    }

    /**
     * @param PositionBlankList the PositionBlankList to set
     */
    public void setPositionBlankList(List<Position> PositionBlankList) {
        this.PositionBlankList = PositionBlankList;
    }

    /**
     * @return the Position
     */
    public Position getPosition() {
        return Position;
    }

    /**
     * @param Position the Position to set
     */
    public void setPosition(Position Position) {
        this.Position = Position;
    }

    /**
     * @return the ResultList
     */
    public List<Result> getResultList() {
        ResultList = em.createQuery("SELECT r FROM Result r").getResultList();
        return ResultList;
    }

    /**
     * @param ResultList the ResultList to set
     */
    public void setResultList(List<Result> ResultList) {
        this.ResultList = ResultList;
    }

    /**
     * @return the ResultBlankList
     */
    public List<Result> getResultBlankList() {
        return ResultBlankList;
    }

    /**
     * @param ResultBlankList the ResultBlankList to set
     */
    public void setResultBlankList(List<Result> ResultBlankList) {
        this.ResultBlankList = ResultBlankList;
    }

    /**
     * @return the Result
     */
    public Result getResult() {
        return Result;
    }

    /**
     * @param Result the Result to set
     */
    public void setResult(Result Result) {
        this.Result = Result;
    }

    /**
     * @return the VoterList
     */
    public List<Voter> getVoterList() {
        VoterList = em.createQuery("SELECT s FROM Voter s").getResultList();
        return VoterList;
    }

    /**
     * @param VoterList the VoterList to set
     */
    public void setVoterList(List<Voter> VoterList) {
        this.VoterList = VoterList;
    }

    /**
     * @return the VoterBlankList
     */
    public List<Voter> getVoterBlankList() {
        return VoterBlankList;
    }

    /**
     * @param VoterBlankList the VoterBlankList to set
     */
    public void setVoterBlankList(List<Voter> VoterBlankList) {
        this.VoterBlankList = VoterBlankList;
    }

    /**
     * @return the Voter
     */
    public Voter getVoter() {
        return Voter;
    }

    /**
     * @param Voter the Voter to set
     */
    public void setVoter(Voter Voter) {
        this.Voter = Voter;
    }

    /**
     * @return the StatusList
     */
    public List<Status> getStatusList() {
        StatusList = em.createQuery("SELECT s FROM Status s").getResultList();
        return StatusList;
    }

    /**
     * @param StatusList the StatusList to set
     */
    public void setStatusList(List<Status> StatusList) {
        this.StatusList = StatusList;
    }

    /**
     * @return the StatusBlankList
     */
    public List<Status> getStatusBlankList() {
        return StatusBlankList;
    }

    /**
     * @param StatusBlankList the StatusBlankList to set
     */
    public void setStatusBlankList(List<Status> StatusBlankList) {
        this.StatusBlankList = StatusBlankList;
    }

    /**
     * @return the Status
     */
    public Status getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(Status Status) {
        this.Status = Status;
    }

    /**
     * @return the registeredCandidates
     */
    public MeterGaugeChartModel getRegisteredCandidates() {
        return registeredCandidates;
    }

    /**
     * @param registeredCandidates the registeredCandidates to set
     */
    public void setRegisteredCandidates(MeterGaugeChartModel registeredCandidates) {
        this.registeredCandidates = registeredCandidates;
    }

    /**
     * @return the loggedSystemUsers
     */
    public MeterGaugeChartModel getLoggedSystemUsers() {
        return loggedSystemUsers;
    }

    /**
     * @param loggedSystemUsers the loggedSystemUsers to set
     */
    public void setLoggedSystemUsers(MeterGaugeChartModel loggedSystemUsers) {
        this.loggedSystemUsers = loggedSystemUsers;
    }

    /**
     * @return the registeredPositions
     */
    public MeterGaugeChartModel getRegisteredPositions() {
        return registeredPositions;
    }

    /**
     * @param registeredPositions the registeredPositions to set
     */
    public void setRegisteredPositions(MeterGaugeChartModel registeredPositions) {
        this.registeredPositions = registeredPositions;
    }

    /**
     * @return the votedVoters
     */
    public MeterGaugeChartModel getVotedVoters() {
        return votedVoters;
    }

    /**
     * @param votedVoters the votedVoters to set
     */
    public void setVotedVoters(MeterGaugeChartModel votedVoters) {
        this.votedVoters = votedVoters;
    }

    /**
     * @return the systemUserTab
     */
    public boolean isSystemUserTab() {
        return systemUserTab;
    }

    /**
     * @param systemUserTab the systemUserTab to set
     */
    public void setSystemUserTab(boolean systemUserTab) {
        this.systemUserTab = systemUserTab;
    }

    /**
     * @return the candidateTab
     */
    public boolean isCandidateTab() {
        return candidateTab;
    }

    /**
     * @param candidateTab the candidateTab to set
     */
    public void setCandidateTab(boolean candidateTab) {
        this.candidateTab = candidateTab;
    }

    /**
     * @return the positionTab
     */
    public boolean isPositionTab() {
        return positionTab;
    }

    /**
     * @param positionTab the positionTab to set
     */
    public void setPositionTab(boolean positionTab) {
        this.positionTab = positionTab;
    }

    /**
     * @return the userGroupTab
     */
    public boolean isUserGroupTab() {
        return userGroupTab;
    }

    /**
     * @param userGroupTab the userGroupTab to set
     */
    public void setUserGroupTab(boolean userGroupTab) {
        this.userGroupTab = userGroupTab;
    }

    /**
     * @return the voterTab
     */
    public boolean isVoterTab() {
        return voterTab;
    }

    /**
     * @param voterTab the voterTab to set
     */
    public void setVoterTab(boolean voterTab) {
        this.voterTab = voterTab;
    }

    /**
     * @return the statusTab
     */
    public boolean isStatusTab() {
        return statusTab;
    }

    /**
     * @param statusTab the statusTab to set
     */
    public void setStatusTab(boolean statusTab) {
        this.statusTab = statusTab;
    }

    /**
     * @return the DepartmentTab
     */
    public boolean isDepartmentTab() {
        return DepartmentTab;
    }

    /**
     * @param DepartmentTab the DepartmentTab to set
     */
    public void setDepartmentTab(boolean DepartmentTab) {
        this.DepartmentTab = DepartmentTab;
    }

    /**
     * @return the DepartmentList
     */
    public List<Department> getDepartmentList() {
        DepartmentList = em.createQuery("SELECT d FROM Department d").getResultList();
        return DepartmentList;
    }

    /**
     * @param DepartmentList the DepartmentList to set
     */
    public void setDepartmentList(List<Department> DepartmentList) {
        this.DepartmentList = DepartmentList;
    }

    /**
     * @return the DepartmentBlankList
     */
    public List<Department> getDepartmentBlankList() {
        return DepartmentBlankList;
    }

    /**
     * @param DepartmentBlankList the DepartmentBlankList to set
     */
    public void setDepartmentBlankList(List<Department> DepartmentBlankList) {
        this.DepartmentBlankList = DepartmentBlankList;
    }

    /**
     * @return the Department
     */
    public Department getDepartment() {
        return Department;
    }

    /**
     * @param Department the Department to set
     */
    public void setDepartment(Department Department) {
        this.Department = Department;
    }

    /**
     * @return the ResultsList
     */
    public List<Results> getResultsList() {
        ResultsList = em.createQuery("select r from Results r").getResultList();
        return ResultsList;
    }

    /**
     * @param ResultsList the ResultsList to set
     */
    public void setResultsList(List<Results> ResultsList) {
        this.ResultsList = ResultsList;
    }

    /**
     * @return the ResultsBlankList
     */
    public List<Results> getResultsBlankList() {
        return ResultsBlankList;
    }

    /**
     * @param ResultsBlankList the ResultsBlankList to set
     */
    public void setResultsBlankList(List<Results> ResultsBlankList) {
        this.ResultsBlankList = ResultsBlankList;
    }

    /**
     * @return the Results
     */
    public Results getResults() {
        return Results;
    }

    /**
     * @param Results the Results to set
     */
    public void setResults(Results Results) {
        this.Results = Results;
    }

    /**
     * @return the QuotesList
     */
    public List<Quotes> getQuotesList() {
        QuotesList = em.createQuery("SELECT q FROM Quotes q").getResultList();
        return QuotesList;
    }

    /**
     * @param QuotesList the QuotesList to set
     */
    public void setQuotesList(List<Quotes> QuotesList) {
        this.QuotesList = QuotesList;
    }

    /**
     * @return the QuotesBlankList
     */
    public List<Quotes> getQuotesBlankList() {
        return QuotesBlankList;
    }

    /**
     * @param QuotesBlankList the QuotesBlankList to set
     */
    public void setQuotesBlankList(List<Quotes> QuotesBlankList) {
        this.QuotesBlankList = QuotesBlankList;
    }

    /**
     * @return the Quotes
     */
    public Quotes getQuotes() {
        return Quotes;
    }

    /**
     * @param Quotes the Quotes to set
     */
    public void setQuotes(Quotes Quotes) {
        this.Quotes = Quotes;
    }

    /**
     * @return the CommentsList
     */
    public List<Comments> getCommentsList() {
        CommentsList = em.createQuery("SELECT c FROM Comments c").getResultList();
        return CommentsList;
    }

    /**
     * @param CommentsList the CommentsList to set
     */
    public void setCommentsList(List<Comments> CommentsList) {
        this.CommentsList = CommentsList;
    }

    /**
     * @return the CommentsBlankList
     */
    public List<Comments> getCommentsBlankList() {
        return CommentsBlankList;
    }

    /**
     * @param CommentsBlankList the CommentsBlankList to set
     */
    public void setCommentsBlankList(List<Comments> CommentsBlankList) {
        this.CommentsBlankList = CommentsBlankList;
    }

    /**
     * @return the Comments
     */
    public Comments getComments() {
        return Comments;
    }

    /**
     * @param Comments the Comments to set
     */
    public void setComments(Comments Comments) {
        this.Comments = Comments;
    }

    /**
     * @return the commentsTab
     */
    public boolean isCommentsTab() {
        return commentsTab;
    }

    /**
     * @param commentsTab the commentsTab to set
     */
    public void setCommentsTab(boolean commentsTab) {
        this.commentsTab = commentsTab;
    }

    /**
     * @return the BlockchainList
     */
    public List<Blockchain> getBlockchainList() {
        BlockchainList = em.createQuery("select b from Blockchain b").getResultList();
        return BlockchainList;
    }

    /**
     * @param BlockchainList the BlockchainList to set
     */
    public void setBlockchainList(List<Blockchain> BlockchainList) {
        this.BlockchainList = BlockchainList;
    }

    /**
     * @return the BlockchainBlankList
     */
    public List<Blockchain> getBlockchainBlankList() {
        return BlockchainBlankList;
    }

    /**
     * @param BlockchainBlankList the BlockchainBlankList to set
     */
    public void setBlockchainBlankList(List<Blockchain> BlockchainBlankList) {
        this.BlockchainBlankList = BlockchainBlankList;
    }

    /**
     * @return the Blockchain
     */
    public Blockchain getBlockchain() {
        return Blockchain;
    }

    /**
     * @param Blockchain the Blockchain to set
     */
    public void setBlockchain(Blockchain Blockchain) {
        this.Blockchain = Blockchain;
    }

}
