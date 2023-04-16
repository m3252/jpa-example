package graph;


import graph.domain.Address;

class GraphMainTest {

    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        System.out.println("a == b ::: " + (a == b));

        Address homeAddress1 = new Address("str1", "str2", "city", "zipcode");
        Address homeAddress2 = new Address("str1", "str2", "city", "zipcode");

        System.out.println("homeAddress1 == homeAddress2 :::" + (homeAddress1 == homeAddress2));
        System.out.println("homeAddress1 eq homeAddress2 :::" + (homeAddress1.equals(homeAddress2)));
    }


}