package ro.ps.showmgmtbackend.service.order;

import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface that defines Order operations
 * This includes:
 * - Finding an order by id
 * - Deleting an order by id
 * - Deleting all orders
 * - Finding all orders
 * - Saving an order
 */
public interface OrderService {
    /**
     * Finds an order by its ID.
     *
     * @param orderId The ID of the order to find
     * @return An OrderResponseDTO representing the found order
     */
    OrderResponseDTO findById(UUID orderId);

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete
     */
    void deleteById(UUID orderId);

    /**
     * Deletes all orders.
     */
    void deleteAll();

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders
     */
    List<OrderResponseDTO> findAll();

    /**
     * Retrieves a paged list of orders.
     *
     * @param page The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of orders
     */
    CollectionResponseDTO<OrderResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Finds orders by show.
     *
     * @param showRequestDTO The ShowRequestDTO representing the show to search for orders
     * @return A list of orders associated with the specified show
     */
    List<OrderResponseDTO> findByShow(ShowRequestDTO showRequestDTO);

    /**
     * Finds orders by user.
     *
     * @param userRequestDTO The UserRequestDTO representing the user to search for orders
     * @return A list of orders associated with the specified user
     */
    List<OrderResponseDTO> findByUser(UserRequestDTO userRequestDTO);

    /**
     * Retrieves a paged list of orders associated with a specific show.
     *
     * @param showRequestDTO The ShowRequestDTO representing the show
     * @param page           The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of orders associated with the specified show
     */
    CollectionResponseDTO<OrderResponseDTO> findAllOrdersFromShowPaged(ShowRequestDTO showRequestDTO, PageRequestDTO page);

    /**
     * Retrieves a paged list of orders associated with a specific user.
     *
     * @param userRequestDTO The UserRequestDTO representing the user
     * @param page           The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of orders associated with the specified user
     */
    CollectionResponseDTO<OrderResponseDTO> findAllOrdersFromUserPaged(UserRequestDTO userRequestDTO, PageRequestDTO page);

    /**
     * Saves a new order.
     *
     * @param orderRequestDTO The OrderRequestDTO containing data for the new order
     * @return An OrderResponseDTO representing the saved order
     */
    OrderResponseDTO save(OrderRequestDTO orderRequestDTO);
}
