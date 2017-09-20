package cysrides.cysrides;

import org.junit.Test;

import static org.junit.Assert.*;

public class OfferServiceImplTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private Collaborator collaborator;
    @Mock
    private OfferJdbc offerJdbc = new OfferJdbcImpl();

    @TestSubject
    private OfferServiceImpl offerService = new OfferServiceImpl();

    private UserInfo userInfo;

    @Before
    public void setup() {
        userInfo = new UserInfo("username", "password", "email", UserType.ADMIN, "firstName", "lastName", "venmoName", 4.5, new List<Offer>());
    }

    @Test
    public void testGetOfferRequests_passenger() {
        userInfo.setUserType(UserType.PASSENGER)
        Offer offer = new Offer(UserType.PASSENGER, 20, userInfo, "destination", "description");
        List<Offer> offerList = new ArrayList<>();
        offerList.add(offer);
        offerList.add(offer);

        EasyMock.expect(offerJdbc.getDriverOffers()).andReturn(offerList);
        replayAll();
        offerService.getOfferRequests(userInfo);
        verifyAll();
    }
}