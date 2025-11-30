/*
import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button, IconButton, styled } from "@mui/material";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {

  fetchSalonBookings,
  updateBookingStatus,
} from "../../../Redux/Booking/action";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

export default function BookingTable() {
  const { salon, service, booking } = useSelector((store) => store);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  React.useEffect(() => {
    
    dispatch(fetchSalonBookings({ jwt: localStorage.getItem("jwt") }));
  }, []);

  
  const handleCancelBooking = (id) => () => {
    // const status=booking.status==="CANCELLED"?"PENDING":"CANCELLED"
    dispatch(
      updateBookingStatus({
        bookingId: id,
        status: "CANCELLED",
        jwt: localStorage.getItem("jwt"),
      })
    );
  };

  return (
    <>
      <h1 className="pb-5 font-bold text-xl">Bookings</h1>

      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Services</StyledTableCell>
              <StyledTableCell>Time & Date</StyledTableCell>
              <StyledTableCell>PRICE</StyledTableCell>
              <StyledTableCell>Customer</StyledTableCell>
              <StyledTableCell>
                Status
              </StyledTableCell>
              <StyledTableCell align="right">Cancel</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {booking.bookings.map((item) => (
              <StyledTableRow key={item.id}>
                <StyledTableCell component="th" scope="row">
                  <ul className="space-y-2">
                    {item.services.map((service) => (
                      <li>{service.name}</li>
                    ))}
                  </ul>
                </StyledTableCell>

                <StyledTableCell className="space-y-2">
                  
                  <p> Date : {item.startTime?.split("T")[0]}</p>
                  <p> Time : {item.startTime?.split("T")[1]}</p>
                </StyledTableCell>
                <StyledTableCell>₹{item.totalPrice}</StyledTableCell>
                <StyledTableCell className="space-y-2">
                  <p>Full Name : {item.customer.fullName}</p>
                  <p>Email : {item.customer.email}</p>
                </StyledTableCell>
                <StyledTableCell >
                  <p className={`${item.status=="CONFIRMED"?"text-green-500":item.status=="PENDING"?"text-blue-500":"text-red-500"} `}>{item.status}</p>
                  </StyledTableCell>
                <StyledTableCell align="right">
                  <Button
                  onClick={handleCancelBooking(item.id)}
                  disabled={item.status === "CANCELLED"}
                    color="error"
                    variant={
                      item.status === "CANCELLED" ? "contained" : "outlined"
                    }
                  >
                    {item.status === "CANCELLED" ? "CANCELLED" : "Cancel"}
                  </Button>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}
  */
 import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button, styled } from "@mui/material";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import {
  fetchSalonBookings,
  updateBookingStatus,
} from "../../../Redux/Booking/action";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${TableCell.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
}));

export default function BookingTable() {
  const { booking, salon } = useSelector((store) => store);

  const salonId = salon?.salon?.id; // FIX #1

  const dispatch = useDispatch();

  React.useEffect(() => {
    dispatch(fetchSalonBookings({ jwt: localStorage.getItem("jwt") }));
  }, []);

  const handleCancelBooking = (id) => {
    dispatch(
      updateBookingStatus({
        bookingId: id,
        status: "CANCELLED",
        jwt: localStorage.getItem("jwt"),
      })
    );
  };

  return (
    <>
      <h1 className="pb-5 font-bold text-xl">Bookings</h1>

      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }}>
          <TableHead>
            <TableRow>
              <StyledTableCell>Services</StyledTableCell>
              <StyledTableCell>Time & Date</StyledTableCell>
              <StyledTableCell>PRICE</StyledTableCell>
              <StyledTableCell>Customer</StyledTableCell>
              <StyledTableCell>Status</StyledTableCell>
              <StyledTableCell align="right">Cancel</StyledTableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {booking.bookings.map((item) => (
              <StyledTableRow key={item.id}>
                <StyledTableCell>
                  <ul>
                    {item.services?.map((s, i) => (
                      <li key={i}>{s.name}</li>
                    ))}
                  </ul>
                </StyledTableCell>

                <StyledTableCell>
                  <p>Date: {item.startTime?.split("T")[0]}</p>
                  <p>Time: {item.startTime?.split("T")[1]?.substring(0, 5)}</p>
                </StyledTableCell>

                <StyledTableCell>₹{item.totalPrice}</StyledTableCell>

                <StyledTableCell>
                  <p>{item.customer?.fullName}</p>
                  <p>{item.customer?.email}</p>
                </StyledTableCell>

                <StyledTableCell>
                  <p
                    className={
                      item.status === "CONFIRMED"
                        ? "text-green-600"
                        : item.status === "PENDING"
                        ? "text-yellow-600"
                        : "text-red-600"
                    }
                  >
                    {item.status}
                  </p>
                </StyledTableCell>

                <StyledTableCell align="right">
                  <Button
                    disabled={item.status === "CANCELLED"}
                    color="error"
                    variant={
                      item.status === "CANCELLED" ? "contained" : "outlined"
                    }
                    onClick={() => handleCancelBooking(item.id)}
                  >
                    {item.status === "CANCELLED" ? "CANCELLED" : "Cancel"}
                  </Button>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}

