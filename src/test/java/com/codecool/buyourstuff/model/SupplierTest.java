package com.codecool.buyourstuff.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {

    private final String NAME_DEFAULT = "BE2team1";
    private final String DESC_DEFAULT = "Random valid description";

    @Test
    void usernameLengthBelowMinimumThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Supplier("LG", DESC_DEFAULT));
    }

    @Test
    void usernameLengthAboveMaximumThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Supplier("1Shenyang Prehistoric Powers Hotel Management Limited Company", DESC_DEFAULT));
    }

    @Test
    void usernameContainsInvalidCharThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Supplier("Abercrombie & Fitch", DESC_DEFAULT));
    }

    @Test
    void descriptionLengthBelowMinimumThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Supplier(NAME_DEFAULT, "no description"));
    }

    @Test
    void descriptionLengthAboveMaximumThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Supplier(NAME_DEFAULT, returnLongText()));
    }

    @Test
    void descriptionContainsInvalidCharConverts() {
        Supplier expected = new Supplier("LG Electronics", "This description will contain certain character ⦅characters？⦆; these should be converted ＆ properly shownǃ");
        Supplier actual = new Supplier("LG Electronics", "This description will contain certain character (characters?); these should be converted & properly shown!");
        assertEquals(expected.getDescription().replaceAll("\\r\\n", "\n"),
                     actual.getDescription().replaceAll("\\r\\n", "\n")
        );
    }
    private static final String returnLongText() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec facilisis dictum euismod. Sed ac augue porta, malesuada ante quis, sodales quam. Nulla facilisi. Fusce sed neque accumsan, ornare purus et, imperdiet purus. Donec sagittis risus tellus, accumsan venenatis magna pellentesque at. Mauris lacinia diam neque, viverra rhoncus sem suscipit id. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Morbi ipsum ante, pulvinar ut ullamcorper vel, tincidunt et ipsum. Morbi sed rhoncus orci.\n" +
                "\n" +
                "Ut vel erat ac arcu dignissim vulputate. Etiam lacus urna, pretium sed elit vel, rhoncus tincidunt diam. Maecenas imperdiet sapien auctor tellus sollicitudin laoreet. Duis nibh turpis, feugiat ut auctor iaculis, commodo vel mauris. Phasellus aliquam efficitur sem in maximus. Praesent nibh enim, aliquet nec rhoncus at, vulputate blandit lorem. Donec vitae leo fringilla, dictum sem eget, vulputate sapien.\n" +
                "\n" +
                "Nam nec lectus eu ex posuere scelerisque at tempus nisi. Nulla vehicula vitae risus id pulvinar. Phasellus nec luctus enim. Nam sit amet ex eget enim porta pulvinar eu non felis. Sed et consequat justo, at sodales purus. Maecenas magna ligula, consectetur nec arcu sed, vulputate suscipit ipsum. Praesent vel leo nisl. Suspendisse quam neque, finibus nec diam et, suscipit faucibus diam. Sed malesuada, ex sit amet tincidunt convallis, mauris quam tempor tellus, non tristique erat nulla sit amet sapien. Vestibulum cursus neque a nibh fringilla interdum. Praesent sit amet turpis ac arcu pharetra volutpat in eu est. Sed in felis nec elit venenatis pellentesque. Vivamus suscipit a est eu mattis. Suspendisse maximus condimentum augue at aliquet. Ut accumsan velit tortor.\n" +
                "\n" +
                "Phasellus rutrum ac est ac cursus. Vestibulum tincidunt id risus a semper. Nullam tempus faucibus ligula eget cursus. Duis sodales, nisl non sollicitudin sodales, ligula lectus lobortis dui, nec auctor metus tortor nec lorem. Cras ante felis, tristique et malesuada quis, suscipit quis nibh. Morbi non feugiat leo. Suspendisse ultrices vitae neque sed posuere. Etiam porta eros nisl, vitae lobortis risus pulvinar ac. Nam eget congue mi. Donec aliquam orci ac diam volutpat ullamcorper.\n" +
                "\n" +
                "Mauris cursus justo tempor tristique ultricies. Aliquam nec magna ipsum. Ut odio dolor, semper eget justo venenatis, blandit consectetur neque. Phasellus mollis faucibus odio eget finibus. Nam non porta ligula. Ut euismod lectus nec justo pulvinar blandit. Nam sed nisi mattis, volutpat felis in, efficitur quam. Proin et convallis turpis. Nunc dignissim at eros a eleifend. Cras nec augue et nunc vulputate pulvinar eu eget ligula. Vestibulum eleifend sagittis velit at accumsan. Ut eget mi pulvinar, mattis arcu eu, consectetur tortor.\n" +
                "\n" +
                "Sed eget nulla ex. Praesent mattis purus et blandit faucibus. Etiam et nunc luctus, tempus mauris ut, molestie elit. Nunc sollicitudin viverra mi, at finibus metus ullamcorper ut. Integer a augue fermentum leo facilisis commodo ac et ipsum. Nunc vel cursus ipsum, eget volutpat nisl. Nam sit amet orci in augue eleifend pretium quis id lectus.\n" +
                "\n" +
                "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras velit ante, iaculis eget pretium id, interdum a augue. In eleifend eleifend diam, a laoreet velit tempor sed. Aliquam faucibus magna eget erat tincidunt fermentum. Nam ultrices ligula id velit interdum, ut pulvinar neque feugiat. Aliquam quis odio quam. Aliquam consectetur nibh felis. Duis non imperdiet erat. Vestibulum accumsan posuere sapien. Donec ac posuere velit, in sodales lectus. Fusce tincidunt velit et faucibus hendrerit. Phasellus quis congue ipsum. Ut hendrerit nisl vel accumsan vulputate.\n" +
                "\n" +
                "Fusce in consequat neque, vel sollicitudin nunc. Nullam semper, sem eu pellentesque accumsan, felis felis lacinia diam, ut mollis justo enim nec mauris. Cras ornare tincidunt leo, non lacinia orci ornare non. Morbi hendrerit erat turpis, nec sodales arcu aliquet eu. Proin augue lacus, sodales id sollicitudin sit amet, mattis vitae diam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Pellentesque imperdiet ornare libero sed consequat. Proin at euismod purus, id pharetra magna. Donec lobortis commodo pellentesque.\n" +
                "\n" +
                "Proin rutrum posuere ultrices. Integer consectetur cursus augue in cursus. Mauris tincidunt varius ex, nec luctus mi efficitur non. Vestibulum orci turpis, auctor sed molestie at, porttitor id risus. Sed mattis ipsum ut scelerisque accumsan. Fusce ac auctor tellus, vel lacinia erat. Ut venenatis sapien vulputate, blandit nulla in, imperdiet ante. Nam ornare nec augue in consectetur. Phasellus fringilla arcu a sem viverra, quis ornare ipsum vulputate. Morbi lorem odio, cursus non molestie nec, tempus vel risus. Maecenas eget tristique libero, vel ornare eros. Suspendisse metus elit, luctus a viverra sit amet, dapibus at leo. Nullam semper lacus urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Etiam fringilla viverra urna nec interdum.\n" +
                "\n" +
                "Aenean a metus in risus gravida lacinia. Vivamus feugiat, tellus et egestas sodales, purus ligula pharetra sapien, ac finibus erat velit eu sapien. Curabitur laoreet vitae ligula ut dictum. Praesent ultrices bibendum arcu, ut iaculis tellus iaculis quis. Vivamus risus enim, pellentesque quis maximus id, cursus in risus. Nunc velit sapien, imperdiet quis eros quis, tincidunt feugiat purus. Aenean nunc velit, tincidunt in mi sit amet, consequat feugiat nisl.\n" +
                "\n" +
                "Nullam maximus euismod lectus vel eleifend. In hac habitasse platea dictumst. Phasellus eu lacus erat. Vivamus ac rutrum velit. Donec sed elit quis erat rutrum feugiat in ut odio. Phasellus scelerisque libero sem, sed sollicitudin leo accumsan nec. Sed neque odio, egestas sed risus vitae, facilisis elementum ante.\n" +
                "\n" +
                "Cras lorem dui, consequat quis lacus eget, accumsan condimentum ante. Sed lobortis eros tincidunt, commodo leo et, vehicula libero. Duis maximus sollicitudin massa ut tincidunt. Donec convallis consectetur magna, sit amet cursus ipsum accumsan eget. Vivamus tempus sodales justo, eget consequat urna. Morbi facilisis dapibus sapien nec ornare. Curabitur mauris ligula, laoreet ut dui quis, dignissim pulvinar lorem. Cras nec est justo. Sed maximus arcu non hendrerit dapibus. Mauris aliquam nisl vel risus hendrerit ultricies. Sed placerat, lectus et scelerisque dapibus, lacus mauris hendrerit erat, vel volutpat orci enim id sapien. Vivamus tortor dui, vehicula at blandit quis, fermentum ut erat. Integer dui ligula, vestibulum ac enim eu, fringilla vulputate nunc.\n" +
                "\n" +
                "Duis scelerisque leo eros, ut laoreet metus porttitor non. Aliquam vehicula odio vitae risus venenatis elementum. Aenean vel bibendum tortor, sit amet mollis ipsum. Nulla ac enim at quam luctus tempus. Nulla nulla orci, porttitor vitae nulla vitae, tristique ornare ex. Nullam non eros mauris. Nullam interdum finibus enim sit amet luctus. Duis nec eros tellus. Integer porttitor varius augue, vel sollicitudin eros euismod nec. Suspendisse ornare tortor sed consequat euismod. Sed imperdiet risus odio, gravida cursus ex ultricies vel. Morbi lectus urna, dignissim sit amet leo a, dapibus sagittis diam. Phasellus sit amet interdum mi. Fusce et tortor id ex auctor posuere vitae ut lacus. Ut ut porta ligula. Suspendisse potenti.\n" +
                "\n" +
                "Vestibulum non quam vitae lorem sagittis pretium eget sed sem. Nunc sodales velit nunc, in blandit turpis interdum nec. Cras vel finibus urna, id gravida felis. Quisque quis varius purus. Proin varius urna a lobortis mollis. In sit amet nibh justo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n" +
                "\n" +
                "Morbi nisi est, porta id arcu vitae, cursus ultricies lorem. Fusce ultrices velit quis turpis aliquet, vitae consequat mauris condimentum. In facilisis, quam ac semper dapibus, orci felis rhoncus justo, et tempor massa risus a dolor. Donec finibus rhoncus nisi, vel imperdiet est hendrerit ut. Nulla convallis metus et diam consectetur condimentum. Duis vitae mattis leo. Proin malesuada sapien a justo aliquet molestie. Quisque et luctus mi, sit amet egestas turpis.\n" +
                "\n" +
                "Morbi risus lorem, luctus a ullamcorper ac, commodo vitae risus. Praesent dapibus ligula nec velit tincidunt, in pellentesque nunc commodo. Pellentesque vestibulum iaculis faucibus. Cras vehicula lacus nunc, eu porttitor neque pulvinar ac. Suspendisse vehicula vitae sem at volutpat. Nam eleifend libero non nunc pulvinar semper. Suspendisse potenti. Sed fringilla magna turpis, eu pretium tortor bibendum ac.\n" +
                "\n" +
                "Proin a dignissim orci. Nunc in sollicitudin ipsum. Donec tempus auctor urna a congue. Maecenas id laoreet enim. Sed euismod orci euismod lorem commodo, eu tristique metus placerat. Etiam finibus in elit quis dignissim. Phasellus purus massa, pulvinar vel lacus a, dignissim consectetur dui. Donec venenatis a purus sed efficitur. Donec imperdiet velit sit amet nibh ultrices, vel gravida odio tempor. Suspendisse tincidunt viverra ex, et bibendum purus malesuada sit amet. Phasellus nec urna dui. Phasellus elementum eros vel nunc porta, eu laoreet lacus semper. Aliquam sed elit massa. Pellentesque eget tempor leo. Nulla nec euismod ipsum, a porttitor mi.\n" +
                "\n" +
                "Nulla lacinia, augue eget molestie ultricies, sapien magna maximus libero, sit amet lacinia mi metus eget lacus. Integer vel lorem imperdiet, placerat purus sed, laoreet quam. Proin eget magna at lectus lacinia faucibus. Sed porttitor tellus vel sem sagittis, id feugiat nunc finibus. Donec tortor lorem, faucibus vel mauris sit amet, iaculis auctor odio. Integer vulputate nibh ante, id mollis dolor faucibus in. Fusce accumsan quam sit amet urna molestie fringilla. Pellentesque in facilisis magna. Etiam eget lacus faucibus arcu sollicitudin molestie. In hac habitasse platea dictumst. Quisque in metus id sem venenatis lacinia.\n" +
                "\n" +
                "Quisque mollis, metus eget interdum pretium, diam nisl volutpat mauris, ac consequat ligula quam vitae lacus. Nam ipsum urna, hendrerit vitae scelerisque ac, egestas eget neque. Nullam ullamcorper ac.";
    }



    @Test
    void lombokNullNameThrows() {
        assertThrows(NullPointerException.class, () ->
                new User(null, DESC_DEFAULT));
    }
    @Test
    void lombokNullDescriptionThrows() {
        assertThrows(NullPointerException.class, () ->
                new User(NAME_DEFAULT, null));
    }
}