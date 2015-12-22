/**
 * Created by kakha on 11/12/2015.
 */
var currentPage = 0;
var currentData;
var productColumns = ["#","სახელი"];
var userColumns = ["სახელი", "გვარი", "მომხმარებლის სახელი", "პირადი ნომერი", "მობილური"];
var filialColumns = ["სახელი", 'მისამართი'];
var regionColumns = ["სახელი"];
var formatColumns = ["სახელი", "ფასი"];
var productRequestsColumns = ["ფილიალი", "თარიღი"];
var zoneColumns = ["სახელი", "რეგიონი"];
var parcelViewColumns = {
    "address": "მისამართი",
    "barcode": "ბარკოდი",
    "expectedDeliveryDate": "სავარაუდო მიტანის დრო",
    "sentFrom": "გაიგზავნა მისამართიდან",
    "format": "ფორმატი",
    "organisation": "ორგანიზაცია",
    "reciever": "მიმღები",
    "status": "სტატუსი",
    "serviceType": "სერვისის ტიპი",
    "deliveryDate": "მიტანის დრო",
    "comment": "კომენტარი"
};
var parcelStatuses = {
    "1": "დარეგისტრირდა",
    "2": "აიღო კურიერმა",
    "3": "შემოვიდა საწყობში",
    "4": "გადაეცა კურიერს",
    "5": "მიტანილია"
};
var userTypes = {
    "1": "sa",
    "2": "ადმინისტრატორი",
    "3": "ფილიალის მომხმარებელი",
    "4": "ტენდერის მომხმარებელი"
};
var canCreateProduct = false;
var canCreateUsers = false;
var topPanelButtons=$("#topPanelButtons");
var addZoneToSelectedVisible=false;