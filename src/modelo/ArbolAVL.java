
package modelo;

public class ArbolAVL <T extends Comparable<T>, E extends Comparable<E>>{
    public Nodo<T, E> root;
    
    public ArbolAVL(){
        this.root = null;
    }
    
    public Nodo<T, E> find(T pLlave){
        return find(pLlave, root) ;
    }

     public Nodo<T, E> find(T pLlave, Nodo<T, E> pNodo ){ /****/
            while( pNodo != null ) {
                if( pLlave.compareTo( pNodo.getLlave() ) < 0 ) {
                    pNodo = pNodo.getLeft();
                }
                else if( pLlave.compareTo( pNodo.getLlave() ) > 0 ) {
                    pNodo = pNodo.getRight();
                }
                else {
                    return pNodo;
                }
            }    // Aquí hubo una coincidencia

            return null;   // No se encontró coincidencia alguna
        }
    
     /*
     * Método que encuentra el mayor dato contenido en el árbol AVL
     */
    public Nodo<T, E> findMax( ){
        return findMax(root);
    }
    
    private Nodo<T, E> findMax(Nodo<T, E> pNodo){
            if( pNodo == null ) {
                return pNodo;
            }
            while( pNodo.getRight() != null ) {
                pNodo = pNodo.getRight();
            }
            return pNodo;
        }
    
    
    public boolean insert(T pLlave, E pElemento){
        this.root = insert(pLlave, pElemento, this.root);
        return root != null;
    }
    
    public Nodo<T, E> insert (T pLlave, E pElemento, Nodo<T, E> pNodo){
        if (pNodo == null) {
            pNodo = new Nodo(pLlave, null, null);
            pNodo.getListaElementos().add(pElemento);
        } else if( pLlave.compareTo(pNodo.getLlave()) < 0 )
           {
            pNodo.setLeft(insert(pLlave, pElemento, pNodo.getLeft()));
            if (height(pNodo.getLeft()) - height(pNodo.getRight()) == 2) {
                if (pLlave.compareTo(pNodo.getLeft().getLlave()) < 0) {
                    pNodo = rotateWithLeftChild(pNodo);
                } else {
                    pNodo = doubleWithLeftChild(pNodo);
                }
            }
        } else if (pLlave.compareTo(pNodo.getLlave()) > 0) {
            pNodo.setRight(insert(pLlave, pElemento, pNodo.getRight()));
            if (height(pNodo.getRight()) - height(pNodo.getLeft()) == 2) {
                if (pLlave.compareTo(pNodo.getRight().getLlave()) > 0) {
                    pNodo = rotateWithRightChild(pNodo);
                } else {
                    pNodo = doubleWithRightChild(pNodo);
                }
            }
        }else { // Si hay un elemento repetido no hacemos nada
//            int index = pNodo.getListaElementos().indexOf(pElemento);
//            
//            if (index == -1) {
//                pNodo.getListaElementos().add(pElemento);
//            }
//            
            
        };  
        
        pNodo.setHeight(max( height( pNodo.left ), height( pNodo.right ) ) + 1);
        return pNodo;
    }
    
    private Nodo<T, E> rotateWithLeftChild( Nodo<T, E> k2 ){
        Nodo<T, E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }
    
    /*
     * Método que genera una roración simple (Caso No 4)
     */
    private Nodo<T, E> rotateWithRightChild( Nodo<T, E> k1 ){
        Nodo<T, E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }
    
    /*
     * Método que genera una doble rotación (Caso No 2)
     */
    
    private Nodo<T, E> doubleWithLeftChild( Nodo<T, E> k3 ){
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }
    
    /*
     * Método que genera una doble rotación (Caso No 3)
     */
    private Nodo<T, E> doubleWithRightChild( Nodo<T, E> k1 ){
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
    
    private int height( Nodo<T, E> pNodo ){
        return pNodo == null ? -1 : pNodo.height;
    }
    
    private static int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }
    
    public boolean isEmpty( ){
        return root == null;
    }
    
    public void printTree( ){
        if(isEmpty()) {
                System.out.println( "El árbol AVL está vacío" );
        }
        else {
            printTree(root);
        }
    }
    
    private void printTree(Nodo<T, E> pNodo){            
            if( pNodo != null ){
                printTree( pNodo.left );
                System.out.println( pNodo.getLlave()+ this.root.listaOcurrencias); /****/
                printTree( pNodo.right );
            }
    }
    
    
    
}
